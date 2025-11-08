package com.stayrooted.repository

import com.stayrooted.domain.Organization
import com.stayrooted.domain.OrganizationType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface OrganizationRepository : JpaRepository<Organization, UUID> {
    // Find organizations by admin user
    fun findByAdminUserIdOrderByCreatedAtDesc(adminUserId: UUID): List<Organization>

    // Find verified organizations
    fun findByIsVerifiedTrueOrderByNameAsc(): List<Organization>

    // Find by organization type
    fun findByOrganizationTypeOrderByNameAsc(type: OrganizationType): List<Organization>

    // Search organizations by name
    @Query("SELECT o FROM Organization o WHERE LOWER(o.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) ORDER BY o.name")
    fun searchByName(searchTerm: String): List<Organization>

    // Find organizations near a location (using raw SQL for PostGIS)
    @Query(
        nativeQuery = true,
        value = """
        SELECT * FROM organizations
        WHERE latitude IS NOT NULL AND longitude IS NOT NULL
        AND ST_DWithin(
            ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)::geography,
            ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography,
            :radiusMeters
        )
        ORDER BY ST_Distance(
            ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)::geography,
            ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography
        )
    """
    )
    fun findNearby(latitude: Double, longitude: Double, radiusMeters: Double): List<Organization>
}
