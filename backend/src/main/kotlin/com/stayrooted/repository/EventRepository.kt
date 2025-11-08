package com.stayrooted.repository

import com.stayrooted.domain.Event
import com.stayrooted.domain.EventType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime
import java.util.UUID

@Repository
interface EventRepository : JpaRepository<Event, UUID> {
    // Find upcoming events
    @Query("SELECT e FROM Event e WHERE e.eventDate >= :now ORDER BY e.eventDate ASC")
    fun findUpcomingEvents(now: OffsetDateTime): List<Event>

    // Find events by organization
    fun findByOrganizationIdOrderByEventDateAsc(organizationId: UUID): List<Event>

    // Find events by type
    fun findByEventTypeAndEventDateGreaterThanEqualOrderByEventDateAsc(
        eventType: EventType,
        now: OffsetDateTime
    ): List<Event>

    // Find events created by user
    fun findByCreatedByIdOrderByEventDateDesc(userId: UUID): List<Event>

    // Search events by title or description
    @Query("""
        SELECT e FROM Event e
        WHERE (LOWER(e.title) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
        OR LOWER(e.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')))
        AND e.eventDate >= :now
        ORDER BY e.eventDate ASC
    """)
    fun searchEvents(searchTerm: String, now: OffsetDateTime): List<Event>

    // Find events near a location (using raw SQL for PostGIS)
    @Query(
        nativeQuery = true,
        value = """
        SELECT * FROM events
        WHERE latitude IS NOT NULL AND longitude IS NOT NULL
        AND event_date >= :now
        AND ST_DWithin(
            ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)::geography,
            ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography,
            :radiusMeters
        )
        ORDER BY event_date ASC
    """
    )
    fun findNearby(
        latitude: Double,
        longitude: Double,
        radiusMeters: Double,
        now: OffsetDateTime
    ): List<Event>

    // Find events near a location by type
    @Query(
        nativeQuery = true,
        value = """
        SELECT * FROM events
        WHERE latitude IS NOT NULL AND longitude IS NOT NULL
        AND event_date >= :now
        AND event_type = :eventType
        AND ST_DWithin(
            ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)::geography,
            ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography,
            :radiusMeters
        )
        ORDER BY event_date ASC
    """
    )
    fun findNearbyByType(
        latitude: Double,
        longitude: Double,
        radiusMeters: Double,
        eventType: String,
        now: OffsetDateTime
    ): List<Event>
}
