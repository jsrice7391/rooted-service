package com.stayrooted.domain

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "daily_content")
data class DailyContent(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(nullable = false, columnDefinition = "TEXT")
    val title: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    val content: String,

    @Column(name = "theologian_name", nullable = false, columnDefinition = "TEXT")
    val theologianName: String,

    @Column(name = "theologian_bio", columnDefinition = "TEXT")
    val theologianBio: String? = null,

    @Column(name = "scripture_reference", columnDefinition = "TEXT")
    val scriptureReference: String? = null,

    @Column(name = "reflection_question", columnDefinition = "TEXT")
    val reflectionQuestion: String? = null,

    @Column(name = "publish_date", nullable = false)
    val publishDate: LocalDate = LocalDate.now(),

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: OffsetDateTime? = null
)
