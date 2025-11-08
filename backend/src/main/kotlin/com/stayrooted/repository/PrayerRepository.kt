package com.stayrooted.repository

import com.stayrooted.domain.Prayer
import com.stayrooted.domain.PrayerVisibility
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PrayerRepository : JpaRepository<Prayer, UUID> {
    // Find user's own prayers
    fun findByUserIdOrderByCreatedAtDesc(userId: UUID): List<Prayer>

    // Find public prayers
    fun findByVisibilityOrderByCreatedAtDesc(visibility: PrayerVisibility): List<Prayer>

    // Find community/public prayers (for feed)
    @Query("SELECT p FROM Prayer p WHERE p.visibility IN ('COMMUNITY', 'PUBLIC') ORDER BY p.createdAt DESC")
    fun findCommunityAndPublicPrayers(): List<Prayer>

    // Find answered prayers (praise reports)
    @Query("SELECT p FROM Prayer p WHERE p.status = 'ANSWERED' AND p.visibility IN ('COMMUNITY', 'PUBLIC') ORDER BY p.answeredAt DESC")
    fun findPraiseReports(): List<Prayer>
}
