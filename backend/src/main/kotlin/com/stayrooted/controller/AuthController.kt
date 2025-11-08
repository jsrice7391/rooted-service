package com.stayrooted.controller

import com.stayrooted.dto.ApiResponse
import com.stayrooted.dto.LoginRequest
import com.stayrooted.dto.LoginResponse
import com.stayrooted.dto.RegisterRequest
import com.stayrooted.service.AuthService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<ApiResponse<LoginResponse>> {
        val response = authService.login(request)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Login successful",
                data = response
            )
        )
    }

    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegisterRequest): ResponseEntity<ApiResponse<LoginResponse>> {
        val response = authService.register(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                success = true,
                message = "User registered successfully",
                data = response
            )
        )
    }
}
