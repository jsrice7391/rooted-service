package com.stayrooted.domain

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(
    name = "organization_members",
    uniqueConstraints = [UniqueConstraint(columnNames = ["organization_id", "user_id"])]
)
data class OrganizationMember(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    val organization: Organization,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: OrganizationRole = OrganizationRole.MEMBER,

    @CreationTimestamp
    @Column(name = "joined_at", nullable = false, updatable = false)
    val joinedAt: OffsetDateTime? = null
)

enum class OrganizationRole {
    ADMIN,
    MODERATOR,
    MEMBER
}
