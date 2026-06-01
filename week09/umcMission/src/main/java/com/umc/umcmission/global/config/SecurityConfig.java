package com.umc.umcmission.global.config;

import com.umc.umcmission.global.security.filter.JwtAuthFilter;
import com.umc.umcmission.global.security.handler.CustomAccessDenied;
import com.umc.umcmission.global.security.handler.CustomEntryPoint;
import com.umc.umcmission.global.security.handler.OAuthSuccessHandler;
import com.umc.umcmission.global.security.service.CustomOAuthService;
import com.umc.umcmission.global.security.service.CustomUserDetailsService;
import com.umc.umcmission.global.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtUtil jwtUtil;
  private final CustomUserDetailsService customUserDetailsService;
  private final CustomOAuthService customOAuthService;

  private final String[] allowUris = {
      "/swagger-ui/**",
      "/swagger-resources/**",
      "/v3/api-docs/**",
      "/api/auth/signup",
      "/api/auth/login",
      "/oauth/authorize/**",
      "/oauth/callback/**"
  };

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .authenticationProvider(daoAuthenticationProvider())
        ///URI 허용 여부
        .authorizeHttpRequests(requests -> requests
            ///Public API 허용
            .requestMatchers(allowUris).permitAll()
            ///이외 API는 인증 필요
            .anyRequest().authenticated()
        )
        ///폼로그인
        .formLogin(AbstractHttpConfigurer::disable)
        /// 세션 (OAuth2 flow는 세션 필요, JWT API는 세션 미사용)
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        )
        /// Jwt 필터
        .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)
        .logout(logout -> logout
            .logoutUrl("/auth/logout")
            .logoutSuccessUrl("/login?logout")
            .logoutSuccessHandler((req, res, auth) -> res.setStatus(200))
            .permitAll()
        )
        /// OAuth2 로그인
        .oauth2Login(oauth -> oauth
            //인증 엔트리 포인트
            .authorizationEndpoint(auth -> auth
                .baseUri("/oauth/authorize")
            )
            //콜백 취소
            .redirectionEndpoint(redirect -> redirect
                .baseUri("/oauth/callback/**")
            )
            //인증 완료 후 정보 활용
            .userInfoEndpoint(userInfo -> userInfo
                .userService(customOAuthService)
            )
            //성공시 JWT 토큰 발행할 핸들러
            .successHandler(oAuthSuccessHandler())
            //실패시 에러 메시지 반환
            .failureHandler((req, res, ex) -> {
                res.setContentType("application/json;charset=UTF-8");
                res.setStatus(401);
                res.getWriter().write("{\"error\":\"" + ex.getMessage() + "\"}");
            })
        )
        /// 예외 상황 핸들러
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
  public JwtAuthFilter jwtAuthFilter() {
    return new JwtAuthFilter(jwtUtil, customUserDetailsService);
  }

  @Bean
  public OAuthSuccessHandler oAuthSuccessHandler() {
    return new OAuthSuccessHandler(jwtUtil);
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
