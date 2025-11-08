package com.stayrooted.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "application.security.jwt")
data class JwtProperties(
    var secretKey: String = "",
    var expiration: Long = 0,
    var refreshExpiration: Long = 0
)
