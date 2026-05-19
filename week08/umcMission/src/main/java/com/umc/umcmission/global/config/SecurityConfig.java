package com.umc.umcmission.global.config;

import com.umc.umcmission.global.security.handler.CustomAccessDenied;
import com.umc.umcmission.global.security.handler.CustomEntryPoint;
import com.umc.umcmission.global.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final CustomUserDetailsService customUserDetailsService;

  private final String[] allowUris = {
      "/swagger-ui/**",
      "/swagger-resources/**",
      "/v3/api-docs/**",
      "/api/auth/signup",
      "/api/auth/login"
  };

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .authenticationProvider(daoAuthenticationProvider())
        .authorizeHttpRequests(requests -> requests
            .requestMatchers(allowUris).permitAll()
            .anyRequest().authenticated()
        )
        .formLogin(AbstractHttpConfigurer::disable)
        .logout(logout -> logout
            .logoutUrl("/auth/logout")
            .logoutSuccessHandler((req, res, auth) -> res.setStatus(200))
            .permitAll()
        )
        .exceptionHandling(exception -> exception
            .accessDeniedHandler(customAccessDenied())
            .authenticationEntryPoint(customEntryPoint())
        );

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailsService);
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
  }

  @Bean
  public CustomAccessDenied customAccessDenied() {
    return new CustomAccessDenied();
  }

  @Bean
  public CustomEntryPoint customEntryPoint() {
    return new CustomEntryPoint();
  }
}
