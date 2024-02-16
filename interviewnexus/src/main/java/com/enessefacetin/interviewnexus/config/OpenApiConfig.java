package com.enessefacetin.interviewnexus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration

public class OpenApiConfig {

    @Bean
    public OpenAPI interviewNexusOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().
                addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes
                ("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info().title("InterviewNexus API")
                .description("InterviewNexus application")
                .version("v1.0.0"));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
            .bearerFormat("JWT")
            .scheme("bearer");
    }
}
