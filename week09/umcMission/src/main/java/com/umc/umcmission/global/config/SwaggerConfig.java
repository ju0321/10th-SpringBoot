package com.umc.umcmission.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI swagger() {
    Info info = new Info().title("UMC10th").description("10기 Swagger").version("0.0.1");

    SecurityScheme bearerScheme = new SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT")
        .in(SecurityScheme.In.HEADER)
        .name("Authorization");

    return new OpenAPI()
        .info(info)
        .addServersItem(new Server().url("/"))
        .components(new Components().addSecuritySchemes("Bearer", bearerScheme))
        .addSecurityItem(new SecurityRequirement().addList("Bearer"));
  }
}