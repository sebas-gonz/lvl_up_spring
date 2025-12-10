package com.lvlupspring.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact; // Correcto: models.info
import io.swagger.v3.oas.models.info.Info;    // Correcto: models.info
import io.swagger.v3.oas.models.info.License; // Correcto: models.info
import io.swagger.v3.oas.models.security.SecurityRequirement; // Correcto: models.security
import io.swagger.v3.oas.models.security.SecurityScheme;      // Correcto: models.security

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de lvl-up")
                        .version("1.0.0")
                        .description("Documentación de la API para el proyecto lvl-up.")
                        .contact(new Contact()
                                .name("Sebastian Gonzalez")
                                .email("seba.gonzalezl@duocuc.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("bearerAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
