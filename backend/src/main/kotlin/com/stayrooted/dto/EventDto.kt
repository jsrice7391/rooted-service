package com.stayrooted.dto

import com.stayrooted.domain.EventType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.UUID

// Request DTOs
data class CreateEventRequest(
    val organizationId: UUID? = null,

    @field:NotBlank(message = "Event title is required")
    val title: String,

    @field:NotBlank(message = "Event description is required")
    val description: String,

    @field:NotNull(message = "Event type is required")
    val eventType: EventType,

    val organizationName: String? = null,

    @field:NotBlank(message = "Location name is required")
    val locationName: String,

    val latitude: BigDecimal? = null,
    val longitude: BigDecimal? = null,

    @field:NotNull(message = "Event date is required")
    val eventDate: OffsetDateTime,

    val contactInfo: String? = null
)

data class UpdateEventRequest(
    val title: String? = null,
    val description: String? = null,
    val eventType: EventType? = null,
    val locationName: String? = null,
    val latitude: BigDecimal? = null,
    val longitude: BigDecimal? = null,
    val eventDate: OffsetDateTime? = null,
    val contactInfo: String? = null
)

data class SearchNearbyEventsRequest(
    @field:NotNull(message = "Latitude is required")
    val latitude: Double,

    @field:NotNull(message = "Longitude is required")
    val longitude: Double,

    val radiusKm: Double = 50.0, // default 50km radius
    val eventType: EventType? = null
)

// Response DTOs
data class EventResponse(
    val id: UUID,
    val organizationId: UUID?,
    val organizationName: String?,
    val organizationLogoUrl: String?,
    val createdByUserId: UUID?,
    val createdByUsername: String?,
    val title: String,
    val description: String,
    val eventType: EventType,
    val locationName: String,
    val latitude: BigDecimal?,
    val longitude: BigDecimal?,
    val eventDate: OffsetDateTime,
    val contactInfo: String?,
    val distanceKm: Double?,
    val createdAt: OffsetDateTime
)

data class EventListResponse(
    val id: UUID,
    val organizationName: String?,
    val title: String,
    val eventType: EventType,
    val locationName: String,
    val eventDate: OffsetDateTime,
    val distanceKm: Double?
)
