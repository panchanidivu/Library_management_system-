package com.example.librarymanagement.config;

import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(title = "Library Management API", version = "1.0", description = "Library Management API"),
    security = @SecurityRequirement(name = "basicAuth")  
)
@SecurityScheme(
    name = "basicAuth", 
    type = SecuritySchemeType.HTTP, 
    scheme = "basic" 
)
public class OpenApiConfig {
}
