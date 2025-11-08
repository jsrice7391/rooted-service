package com.stayrooted.domain

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "prayers")
data class Prayer(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    var content: String,

    @Column(name = "scripture_reference")
    var scriptureReference: String? = null,

    @Column(name = "scripture_text", columnDefinition = "TEXT")
    var scriptureText: String? = null,

    @Column(name = "youtube_music_url")
    var youtubeMusicUrl: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: PrayerStatus = PrayerStatus.PENDING,

    @Column(name = "answered_at")
    var answeredAt: OffsetDateTime? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var visibility: PrayerVisibility = PrayerVisibility.PRIVATE,

    @Column(name = "is_shared")
    var isShared: Boolean = false,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: OffsetDateTime? = null,

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    val updatedAt: OffsetDateTime? = null
)

enum class PrayerStatus {
    PENDING,
    ANSWERED
}

enum class PrayerVisibility {
    PRIVATE,      // Only visible to the user (journaling)
    COMMUNITY,    // Visible to community members
    PUBLIC        // Visible to everyone
}
