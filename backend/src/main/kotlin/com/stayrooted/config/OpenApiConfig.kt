package com.stayrooted.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.Components
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        val securitySchemeName = "bearerAuth"
        return OpenAPI()
            .info(
                Info()
                    .title("StayRooted API")
                    .version("1.0.0")
                    .description("API documentation for the StayRooted application - a faith-based community platform for prayers, events, and spiritual growth.")
                    .contact(
                        Contact()
                            .name("StayRooted Team")
                            .email("support@stayrooted.com")
                    )
            )
            .components(
                Components()
                    .addSecuritySchemes(
                        securitySchemeName,
                        SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                            .description("JWT token obtained from /api/auth/login or /api/auth/register")
                    )
            )
            .addSecurityItem(SecurityRequirement().addList(securitySchemeName))
    }
}
