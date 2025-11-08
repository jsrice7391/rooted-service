package com.stayrooted.dto

import java.time.LocalDateTime

data class ErrorResponse(
    val timestamp: LocalDateTime,
    val status: Int,
    val error: String,
    val message: String,
    val validationErrors: Map<String, String>? = null
)
