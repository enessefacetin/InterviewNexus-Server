package com.enessefacetin.interviewnexus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI interviewNexusOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("InterviewNexus API")
                .description("InterviewNexus application")
                .version("v1.0.0"));
    }
}