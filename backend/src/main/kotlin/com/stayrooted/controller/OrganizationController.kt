package com.stayrooted.controller

import com.stayrooted.domain.OrganizationRole
import com.stayrooted.dto.*
import com.stayrooted.service.OrganizationService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/organizations")
class OrganizationController(
    private val organizationService: OrganizationService
) {

    @PostMapping
    fun createOrganization(
        @Valid @RequestBody request: CreateOrganizationRequest,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<OrganizationResponse>> {
        val userId = UUID.fromString(authentication.name)
        val organization = organizationService.createOrganization(userId, request)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                success = true,
                message = "Organization created successfully",
                data = organization
            )
        )
    }

    @GetMapping("/{organizationId}")
    fun getOrganizationById(
        @PathVariable organizationId: UUID,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<OrganizationResponse>> {
        val userId = UUID.fromString(authentication.name)
        val organization = organizationService.getOrganizationById(organizationId, userId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Organization retrieved successfully",
                data = organization
            )
        )
    }

    @GetMapping
    fun getAllOrganizations(authentication: Authentication): ResponseEntity<ApiResponse<List<OrganizationResponse>>> {
        val userId = UUID.fromString(authentication.name)
        val organizations = organizationService.getAllOrganizations(userId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Organizations retrieved successfully",
                data = organizations
            )
        )
    }

    @GetMapping("/verified")
    fun getVerifiedOrganizations(authentication: Authentication): ResponseEntity<ApiResponse<List<OrganizationResponse>>> {
        val userId = UUID.fromString(authentication.name)
        val organizations = organizationService.getVerifiedOrganizations(userId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Verified organizations retrieved successfully",
                data = organizations
            )
        )
    }

    @GetMapping("/search")
    fun searchOrganizations(
        @RequestParam searchTerm: String,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<List<OrganizationResponse>>> {
        val userId = UUID.fromString(authentication.name)
        val organizations = organizationService.searchOrganizations(searchTerm, userId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Search results retrieved successfully",
                data = organizations
            )
        )
    }

    @GetMapping("/nearby")
    fun findNearbyOrganizations(
        @RequestParam latitude: Double,
        @RequestParam longitude: Double,
        @RequestParam(defaultValue = "50.0") radiusKm: Double,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<List<OrganizationResponse>>> {
        val userId = UUID.fromString(authentication.name)
        val organizations = organizationService.findNearbyOrganizations(latitude, longitude, radiusKm, userId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Nearby organizations retrieved successfully",
                data = organizations
            )
        )
    }

    @GetMapping("/my-organizations")
    fun getMyOrganizations(authentication: Authentication): ResponseEntity<ApiResponse<List<OrganizationResponse>>> {
        val userId = UUID.fromString(authentication.name)
        val organizations = organizationService.getMyOrganizations(userId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Your organizations retrieved successfully",
                data = organizations
            )
        )
    }

    @PutMapping("/{organizationId}")
    fun updateOrganization(
        @PathVariable organizationId: UUID,
        @Valid @RequestBody request: UpdateOrganizationRequest,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<OrganizationResponse>> {
        val userId = UUID.fromString(authentication.name)
        val organization = organizationService.updateOrganization(organizationId, userId, request)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Organization updated successfully",
                data = organization
            )
        )
    }

    @DeleteMapping("/{organizationId}")
    fun deleteOrganization(
        @PathVariable organizationId: UUID,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<Unit>> {
        val userId = UUID.fromString(authentication.name)
        organizationService.deleteOrganization(organizationId, userId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Organization deleted successfully",
                data = Unit
            )
        )
    }

    @PostMapping("/{organizationId}/join")
    fun joinOrganization(
        @PathVariable organizationId: UUID,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<OrganizationResponse>> {
        val userId = UUID.fromString(authentication.name)
        val organization = organizationService.joinOrganization(organizationId, userId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Successfully joined organization",
                data = organization
            )
        )
    }

    @DeleteMapping("/{organizationId}/leave")
    fun leaveOrganization(
        @PathVariable organizationId: UUID,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<Unit>> {
        val userId = UUID.fromString(authentication.name)
        organizationService.leaveOrganization(organizationId, userId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Successfully left organization",
                data = Unit
            )
        )
    }

    @GetMapping("/{organizationId}/members")
    fun getOrganizationMembers(
        @PathVariable organizationId: UUID
    ): ResponseEntity<ApiResponse<List<OrganizationMemberResponse>>> {
        val members = organizationService.getOrganizationMembers(organizationId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Organization members retrieved successfully",
                data = members
            )
        )
    }

    @PutMapping("/{organizationId}/members/{memberId}/role")
    fun updateMemberRole(
        @PathVariable organizationId: UUID,
        @PathVariable memberId: UUID,
        @RequestParam role: OrganizationRole,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<OrganizationMemberResponse>> {
        val userId = UUID.fromString(authentication.name)
        val member = organizationService.updateMemberRole(organizationId, memberId, role, userId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Member role updated successfully",
                data = member
            )
        )
    }
}
