package com.stayrooted.domain

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "events")
data class Event(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    val organization: Organization? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    val createdBy: User? = null,

    @Column(nullable = false, columnDefinition = "TEXT")
    var title: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    var description: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false)
    var eventType: EventType,

    @Column(name = "organization_name", columnDefinition = "TEXT")
    var organizationName: String? = null,

    @Column(name = "location_name", nullable = false, columnDefinition = "TEXT")
    var locationName: String,

    @Column(precision = 10, scale = 8)
    var latitude: BigDecimal? = null,

    @Column(precision = 11, scale = 8)
    var longitude: BigDecimal? = null,

    @Column(name = "event_date", nullable = false)
    var eventDate: OffsetDateTime,

    @Column(name = "contact_info", columnDefinition = "TEXT")
    var contactInfo: String? = null,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: OffsetDateTime? = null
)

enum class EventType {
    EVANGELISTIC_OUTREACH,
    BIBLE_STUDY,
    PRAYER_MEETING,
    WORSHIP_NIGHT
}
