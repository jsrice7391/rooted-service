package com.stayrooted.dto

import com.stayrooted.domain.PrayerStatus
import com.stayrooted.domain.PrayerVisibility
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.OffsetDateTime
import java.util.UUID

// Request DTOs
data class CreatePrayerRequest(
    @field:NotBlank(message = "Title is required")
    val title: String,

    @field:NotBlank(message = "Content is required")
    val content: String,

    val scriptureReference: String? = null,
    val scriptureText: String? = null,
    val youtubeMusicUrl: String? = null,

    @field:NotNull(message = "Visibility is required")
    val visibility: PrayerVisibility
)

data class UpdatePrayerRequest(
    val title: String? = null,
    val content: String? = null,
    val scriptureReference: String? = null,
    val scriptureText: String? = null,
    val youtubeMusicUrl: String? = null,
    val visibility: PrayerVisibility? = null
)

data class MarkPrayerAnsweredRequest(
    @field:NotBlank(message = "Testimony or update is required")
    val testimony: String
)

data class CreatePrayerUpdateRequest(
    @field:NotBlank(message = "Update content is required")
    val content: String
)

// Response DTOs
data class PrayerResponse(
    val id: UUID,
    val userId: UUID,
    val username: String,
    val userFullName: String,
    val title: String,
    val content: String,
    val scriptureReference: String?,
    val scriptureText: String?,
    val youtubeMusicUrl: String?,
    val status: PrayerStatus,
    val answeredAt: OffsetDateTime?,
    val visibility: PrayerVisibility,
    val isShared: Boolean,
    val followerCount: Long,
    val updateCount: Long,
    val isFollowing: Boolean,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime
)

data class PrayerUpdateResponse(
    val id: UUID,
    val prayerId: UUID,
    val content: String,
    val createdAt: OffsetDateTime
)

data class PrayerFollowerResponse(
    val id: UUID,
    val userId: UUID,
    val username: String,
    val fullName: String,
    val createdAt: OffsetDateTime
)

data class PrayerStatsResponse(
    val totalPrayers: Long,
    val pendingPrayers: Long,
    val answeredPrayers: Long,
    val prayersFollowing: Long
)
