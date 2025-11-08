package com.stayrooted.repository

import com.stayrooted.domain.PrayerFollower
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface PrayerFollowerRepository : JpaRepository<PrayerFollower, UUID> {
    // Check if user is following a prayer
    fun existsByPrayerIdAndUserId(prayerId: UUID, userId: UUID): Boolean

    // Find follower record
    fun findByPrayerIdAndUserId(prayerId: UUID, userId: UUID): Optional<PrayerFollower>

    // Count followers for a prayer
    fun countByPrayerId(prayerId: UUID): Long

    // Get all prayers a user is following
    fun findByUserIdOrderByCreatedAtDesc(userId: UUID): List<PrayerFollower>

    // Get all followers of a prayer
    fun findByPrayerIdOrderByCreatedAtDesc(prayerId: UUID): List<PrayerFollower>
}
