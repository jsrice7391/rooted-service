package com.stayrooted.service

import com.stayrooted.domain.User
import com.stayrooted.dto.LoginRequest
import com.stayrooted.dto.LoginResponse
import com.stayrooted.dto.RegisterRequest
import com.stayrooted.repository.UserRepository
import com.stayrooted.security.JwtService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtService: JwtService
) {

    @Transactional
    fun login(request: LoginRequest): LoginResponse {
        // Find user by username
        val user = userRepository.findByUsername(request.username)
            .orElseThrow { IllegalArgumentException("User not found with username: ${request.username}") }

        // For now, we're not validating the password as requested
        // TODO: Add password validation later

        // Check if user is active
        if (!user.isActive) {
            throw IllegalArgumentException("User account is inactive")
        }

        // Generate JWT token
        val token = jwtService.generateToken(user.username, user.id!!)

        return LoginResponse(
            token = token,
            username = user.username,
            email = user.email,
            fullName = user.fullName
        )
    }

    @Transactional
    fun register(request: RegisterRequest): LoginResponse {
        // Check if username already exists
        if (userRepository.existsByUsername(request.username)) {
            throw IllegalArgumentException("Username already exists: ${request.username}")
        }

        // Check if email already exists
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("Email already exists: ${request.email}")
        }

        // Create new user (storing plain password for now - not secure!)
        // TODO: Hash password before storing
        val user = User(
            username = request.username,
            password = request.password, // NOT HASHED - temporary for development
            email = request.email,
            fullName = request.fullName,
            isActive = true
        )

        val savedUser = userRepository.save(user)

        // Generate JWT token
        val token = jwtService.generateToken(savedUser.username, savedUser.id!!)

        return LoginResponse(
            token = token,
            username = savedUser.username,
            email = savedUser.email,
            fullName = savedUser.fullName
        )
    }
}
