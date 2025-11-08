package com.stayrooted.repository

import com.stayrooted.domain.OrganizationMember
import com.stayrooted.domain.OrganizationRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface OrganizationMemberRepository : JpaRepository<OrganizationMember, UUID> {
    // Check if user is a member of organization
    fun existsByOrganizationIdAndUserId(organizationId: UUID, userId: UUID): Boolean

    // Find membership record
    fun findByOrganizationIdAndUserId(organizationId: UUID, userId: UUID): Optional<OrganizationMember>

    // Get all members of an organization
    fun findByOrganizationIdOrderByJoinedAtDesc(organizationId: UUID): List<OrganizationMember>

    // Get all organizations a user is a member of
    fun findByUserIdOrderByJoinedAtDesc(userId: UUID): List<OrganizationMember>

    // Get admins and moderators of an organization
    fun findByOrganizationIdAndRoleInOrderByJoinedAtAsc(
        organizationId: UUID,
        roles: List<OrganizationRole>
    ): List<OrganizationMember>

    // Count members
    fun countByOrganizationId(organizationId: UUID): Long
}
