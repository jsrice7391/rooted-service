package com.stayrooted.service

import com.stayrooted.domain.Organization
import com.stayrooted.domain.OrganizationMember
import com.stayrooted.domain.OrganizationRole
import com.stayrooted.dto.*
import com.stayrooted.repository.OrganizationMemberRepository
import com.stayrooted.repository.OrganizationRepository
import com.stayrooted.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class OrganizationService(
    private val organizationRepository: OrganizationRepository,
    private val organizationMemberRepository: OrganizationMemberRepository,
    private val userRepository: UserRepository
) {

    @Transactional
    fun createOrganization(userId: UUID, request: CreateOrganizationRequest): OrganizationResponse {
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User not found") }

        val organization = Organization(
            name = request.name,
            description = request.description,
            organizationType = request.organizationType,
            contactEmail = request.contactEmail,
            contactPhone = request.contactPhone,
            websiteUrl = request.websiteUrl,
            logoUrl = request.logoUrl,
            address = request.address,
            city = request.city,
            state = request.state,
            zipCode = request.zipCode,
            country = request.country ?: "USA",
            latitude = request.latitude,
            longitude = request.longitude,
            adminUser = user,
            isVerified = false
        )

        val savedOrganization = organizationRepository.save(organization)

        // Auto-add creator as admin member
        val adminMember = OrganizationMember(
            organization = savedOrganization,
            user = user,
            role = OrganizationRole.ADMIN
        )
        organizationMemberRepository.save(adminMember)

        return toOrganizationResponse(savedOrganization, userId)
    }

    @Transactional(readOnly = true)
    fun getOrganizationById(organizationId: UUID, currentUserId: UUID): OrganizationResponse {
        val organization = organizationRepository.findById(organizationId)
            .orElseThrow { IllegalArgumentException("Organization not found") }
        return toOrganizationResponse(organization, currentUserId)
    }

    @Transactional(readOnly = true)
    fun getAllOrganizations(currentUserId: UUID): List<OrganizationResponse> {
        return organizationRepository.findAll()
            .map { toOrganizationResponse(it, currentUserId) }
    }

    @Transactional(readOnly = true)
    fun getVerifiedOrganizations(currentUserId: UUID): List<OrganizationResponse> {
        return organizationRepository.findByIsVerifiedTrueOrderByNameAsc()
            .map { toOrganizationResponse(it, currentUserId) }
    }

    @Transactional(readOnly = true)
    fun searchOrganizations(searchTerm: String, currentUserId: UUID): List<OrganizationResponse> {
        return organizationRepository.searchByName(searchTerm)
            .map { toOrganizationResponse(it, currentUserId) }
    }

    @Transactional(readOnly = true)
    fun findNearbyOrganizations(latitude: Double, longitude: Double, radiusKm: Double, currentUserId: UUID): List<OrganizationResponse> {
        val radiusMeters = radiusKm * 1000
        return organizationRepository.findNearby(latitude, longitude, radiusMeters)
            .map { toOrganizationResponse(it, currentUserId) }
    }

    @Transactional(readOnly = true)
    fun getMyOrganizations(userId: UUID): List<OrganizationResponse> {
        return organizationMemberRepository.findByUserIdOrderByJoinedAtDesc(userId)
            .map { toOrganizationResponse(it.organization, userId) }
    }

    @Transactional
    fun updateOrganization(organizationId: UUID, userId: UUID, request: UpdateOrganizationRequest): OrganizationResponse {
        val organization = organizationRepository.findById(organizationId)
            .orElseThrow { IllegalArgumentException("Organization not found") }

        // Only admin can update
        if (!isOrganizationAdmin(organizationId, userId)) {
            throw IllegalAccessException("Only organization admins can update the organization")
        }

        request.name?.let { organization.name = it }
        request.description?.let { organization.description = it }
        request.organizationType?.let { organization.organizationType = it }
        request.contactEmail?.let { organization.contactEmail = it }
        request.contactPhone?.let { organization.contactPhone = it }
        request.websiteUrl?.let { organization.websiteUrl = it }
        request.logoUrl?.let { organization.logoUrl = it }
        request.address?.let { organization.address = it }
        request.city?.let { organization.city = it }
        request.state?.let { organization.state = it }
        request.zipCode?.let { organization.zipCode = it }
        request.country?.let { organization.country = it }
        request.latitude?.let { organization.latitude = it }
        request.longitude?.let { organization.longitude = it }

        val updatedOrganization = organizationRepository.save(organization)
        return toOrganizationResponse(updatedOrganization, userId)
    }

    @Transactional
    fun deleteOrganization(organizationId: UUID, userId: UUID) {
        val organization = organizationRepository.findById(organizationId)
            .orElseThrow { IllegalArgumentException("Organization not found") }

        // Only the main admin (creator) can delete
        if (organization.adminUser.id != userId) {
            throw IllegalAccessException("Only the organization creator can delete it")
        }

        organizationRepository.delete(organization)
    }

    @Transactional
    fun joinOrganization(organizationId: UUID, userId: UUID): OrganizationResponse {
        val organization = organizationRepository.findById(organizationId)
            .orElseThrow { IllegalArgumentException("Organization not found") }

        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User not found") }

        // Check if already a member
        if (organizationMemberRepository.existsByOrganizationIdAndUserId(organizationId, userId)) {
            throw IllegalStateException("You are already a member of this organization")
        }

        val member = OrganizationMember(
            organization = organization,
            user = user,
            role = OrganizationRole.MEMBER
        )

        organizationMemberRepository.save(member)
        return toOrganizationResponse(organization, userId)
    }

    @Transactional
    fun leaveOrganization(organizationId: UUID, userId: UUID) {
        val organization = organizationRepository.findById(organizationId)
            .orElseThrow { IllegalArgumentException("Organization not found") }

        // Can't leave if you're the main admin
        if (organization.adminUser.id == userId) {
            throw IllegalStateException("Organization creator cannot leave. Transfer ownership or delete the organization.")
        }

        val member = organizationMemberRepository.findByOrganizationIdAndUserId(organizationId, userId)
            .orElseThrow { IllegalArgumentException("You are not a member of this organization") }

        organizationMemberRepository.delete(member)
    }

    @Transactional
    fun updateMemberRole(organizationId: UUID, memberId: UUID, newRole: OrganizationRole, adminUserId: UUID): OrganizationMemberResponse {
        // Only admin can update roles
        if (!isOrganizationAdmin(organizationId, adminUserId)) {
            throw IllegalAccessException("Only organization admins can update member roles")
        }

        val member = organizationMemberRepository.findById(memberId)
            .orElseThrow { IllegalArgumentException("Member not found") }

        if (member.organization.id != organizationId) {
            throw IllegalArgumentException("Member does not belong to this organization")
        }

        member.role = newRole
        val updatedMember = organizationMemberRepository.save(member)

        return toOrganizationMemberResponse(updatedMember)
    }

    @Transactional(readOnly = true)
    fun getOrganizationMembers(organizationId: UUID): List<OrganizationMemberResponse> {
        return organizationMemberRepository.findByOrganizationIdOrderByJoinedAtDesc(organizationId)
            .map { toOrganizationMemberResponse(it) }
    }

    // Helper methods
    private fun isOrganizationAdmin(organizationId: UUID, userId: UUID): Boolean {
        val member = organizationMemberRepository.findByOrganizationIdAndUserId(organizationId, userId)
        return member.isPresent && member.get().role == OrganizationRole.ADMIN
    }

    private fun toOrganizationResponse(organization: Organization, currentUserId: UUID): OrganizationResponse {
        val memberCount = organizationMemberRepository.countByOrganizationId(organization.id!!)
        val membership = organizationMemberRepository.findByOrganizationIdAndUserId(organization.id, currentUserId)

        return OrganizationResponse(
            id = organization.id,
            name = organization.name,
            description = organization.description,
            organizationType = organization.organizationType,
            contactEmail = organization.contactEmail,
            contactPhone = organization.contactPhone,
            websiteUrl = organization.websiteUrl,
            logoUrl = organization.logoUrl,
            address = organization.address,
            city = organization.city,
            state = organization.state,
            zipCode = organization.zipCode,
            country = organization.country,
            latitude = organization.latitude,
            longitude = organization.longitude,
            adminUserId = organization.adminUser.id!!,
            adminUsername = organization.adminUser.username,
            isVerified = organization.isVerified,
            memberCount = memberCount,
            isMember = membership.isPresent,
            userRole = membership.map { it.role.name }.orElse(null),
            createdAt = organization.createdAt!!,
            updatedAt = organization.updatedAt!!
        )
    }

    private fun toOrganizationMemberResponse(member: OrganizationMember): OrganizationMemberResponse {
        return OrganizationMemberResponse(
            id = member.id!!,
            userId = member.user.id!!,
            username = member.user.username,
            fullName = member.user.fullName,
            role = member.role.name,
            joinedAt = member.joinedAt!!
        )
    }
}
