package com.stayrooted.repository

import com.stayrooted.domain.PrayerUpdate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PrayerUpdateRepository : JpaRepository<PrayerUpdate, UUID> {
    // Get all updates for a prayer in chronological order
    fun findByPrayerIdOrderByCreatedAtAsc(prayerId: UUID): List<PrayerUpdate>

    // Count updates for a prayer
    fun countByPrayerId(prayerId: UUID): Long
}
