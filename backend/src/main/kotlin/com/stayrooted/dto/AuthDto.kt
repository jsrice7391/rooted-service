package com.stayrooted.dto

import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @field:NotBlank(message = "Username is required")
    val username: String,

    @field:NotBlank(message = "Password is required")
    val password: String
)

data class LoginResponse(
    val token: String,
    val username: String,
    val email: String,
    val fullName: String
)

data class RegisterRequest(
    @field:NotBlank(message = "Username is required")
    val username: String,

    @field:NotBlank(message = "Password is required")
    val password: String,

    @field:NotBlank(message = "Email is required")
    val email: String,

    @field:NotBlank(message = "Full name is required")
    val fullName: String
)
