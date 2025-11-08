package com.stayrooted.dto

import com.stayrooted.domain.OrganizationType
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.UUID

// Request DTOs
data class CreateOrganizationRequest(
    @field:NotBlank(message = "Organization name is required")
    val name: String,

    val description: String? = null,

    @field:NotNull(message = "Organization type is required")
    val organizationType: OrganizationType,

    @field:Email(message = "Invalid email format")
    val contactEmail: String? = null,

    val contactPhone: String? = null,
    val websiteUrl: String? = null,
    val logoUrl: String? = null,
    val address: String? = null,
    val city: String? = null,
    val state: String? = null,
    val zipCode: String? = null,
    val country: String? = "USA",
    val latitude: BigDecimal? = null,
    val longitude: BigDecimal? = null
)

data class UpdateOrganizationRequest(
    val name: String? = null,
    val description: String? = null,
    val organizationType: OrganizationType? = null,
    val contactEmail: String? = null,
    val contactPhone: String? = null,
    val websiteUrl: String? = null,
    val logoUrl: String? = null,
    val address: String? = null,
    val city: String? = null,
    val state: String? = null,
    val zipCode: String? = null,
    val country: String? = null,
    val latitude: BigDecimal? = null,
    val longitude: BigDecimal? = null
)

// Response DTOs
data class OrganizationResponse(
    val id: UUID,
    val name: String,
    val description: String?,
    val organizationType: OrganizationType,
    val contactEmail: String?,
    val contactPhone: String?,
    val websiteUrl: String?,
    val logoUrl: String?,
    val address: String?,
    val city: String?,
    val state: String?,
    val zipCode: String?,
    val country: String,
    val latitude: BigDecimal?,
    val longitude: BigDecimal?,
    val adminUserId: UUID,
    val adminUsername: String,
    val isVerified: Boolean,
    val memberCount: Long,
    val isMember: Boolean,
    val userRole: String?,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime
)

data class OrganizationMemberResponse(
    val id: UUID,
    val userId: UUID,
    val username: String,
    val fullName: String,
    val role: String,
    val joinedAt: OffsetDateTime
)

data class OrganizationListResponse(
    val id: UUID,
    val name: String,
    val organizationType: OrganizationType,
    val city: String?,
    val state: String?,
    val isVerified: Boolean,
    val memberCount: Long,
    val logoUrl: String?
)
