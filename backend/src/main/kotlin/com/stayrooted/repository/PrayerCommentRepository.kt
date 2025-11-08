package com.stayrooted.repository

import com.stayrooted.domain.PrayerComment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PrayerCommentRepository : JpaRepository<PrayerComment, UUID> {
    // Get all comments for a prayer
    fun findByPrayerIdOrderByCreatedAtAsc(prayerId: UUID): List<PrayerComment>

    // Count comments for a prayer
    fun countByPrayerId(prayerId: UUID): Long

    // Get user's comments
    fun findByUserIdOrderByCreatedAtDesc(userId: UUID): List<PrayerComment>
}
