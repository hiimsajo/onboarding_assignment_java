package com.example.onboarding_assignment.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Onboarding 과제: USER API",
        version = "v1",
        description = "회원가입과 로그인 API 입니다"
    )
)
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI() {
    // Security Scheme (Bearer Token)
    SecurityScheme apiKey = new SecurityScheme()
        .type(SecurityScheme.Type.APIKEY)
        .in(SecurityScheme.In.HEADER)
        .name("Authorization");

    SecurityRequirement securityRequirement = new SecurityRequirement()
        .addList("Bearer Token");

    // OpenAPI 설정 (CORS와 API 보안)
    return new OpenAPI()
        .components(new Components().addSecuritySchemes("Bearer Token", apiKey))
        .addSecurityItem(securityRequirement);
  }
}