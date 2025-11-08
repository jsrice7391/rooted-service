package com.stayrooted.domain

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "organizations")
data class Organization(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(nullable = false, columnDefinition = "TEXT")
    var name: String,

    @Column(columnDefinition = "TEXT")
    var description: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "organization_type", nullable = false)
    var organizationType: OrganizationType,

    @Column(name = "contact_email")
    var contactEmail: String? = null,

    @Column(name = "contact_phone")
    var contactPhone: String? = null,

    @Column(name = "website_url")
    var websiteUrl: String? = null,

    @Column(name = "logo_url")
    var logoUrl: String? = null,

    @Column
    var address: String? = null,

    @Column
    var city: String? = null,

    @Column
    var state: String? = null,

    @Column(name = "zip_code")
    var zipCode: String? = null,

    @Column
    var country: String = "USA",

    @Column(precision = 10, scale = 8)
    var latitude: BigDecimal? = null,

    @Column(precision = 11, scale = 8)
    var longitude: BigDecimal? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_user_id", nullable = false)
    val adminUser: User,

    @Column(name = "is_verified")
    var isVerified: Boolean = false,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: OffsetDateTime? = null,

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    val updatedAt: OffsetDateTime? = null
)

enum class OrganizationType {
    CHURCH,
    MINISTRY,
    NONPROFIT,
    OTHER
}
