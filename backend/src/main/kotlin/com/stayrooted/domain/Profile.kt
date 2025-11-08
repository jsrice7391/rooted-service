package com.stayrooted.domain

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "profiles")
data class Profile(
    @Id
    val id: UUID,

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    val user: User,

    @Column(name = "full_name")
    var fullName: String? = null,

    @Column(name = "avatar_url")
    var avatarUrl: String? = null,

    @Column
    var bio: String? = null,

    @Column
    var location: String? = null,

    @Column(name = "daily_streak")
    var dailyStreak: Int = 0,

    @Column(name = "last_active_date")
    var lastActiveDate: LocalDate? = null,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: OffsetDateTime? = null,

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    val updatedAt: OffsetDateTime? = null
)
