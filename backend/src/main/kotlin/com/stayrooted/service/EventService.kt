package com.stayrooted.service

import com.stayrooted.domain.Event
import com.stayrooted.dto.*
import com.stayrooted.repository.EventRepository
import com.stayrooted.repository.OrganizationMemberRepository
import com.stayrooted.repository.OrganizationRepository
import com.stayrooted.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime
import java.util.UUID
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@Service
class EventService(
    private val eventRepository: EventRepository,
    private val organizationRepository: OrganizationRepository,
    private val organizationMemberRepository: OrganizationMemberRepository,
    private val userRepository: UserRepository
) {

    @Transactional
    fun createEvent(userId: UUID, request: CreateEventRequest): EventResponse {
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User not found") }

        // If organizationId is provided, verify user has permission
        val organization = request.organizationId?.let { orgId ->
            val org = organizationRepository.findById(orgId)
                .orElseThrow { IllegalArgumentException("Organization not found") }

            // Check if user is admin or moderator of the organization
            val membership = organizationMemberRepository.findByOrganizationIdAndUserId(orgId, userId)
                .orElseThrow { IllegalAccessException("You must be a member of the organization to create events") }

            if (membership.role.name !in listOf("ADMIN", "MODERATOR")) {
                throw IllegalAccessException("Only organization admins or moderators can create events")
            }

            org
        }

        val event = Event(
            organization = organization,
            createdBy = user,
            title = request.title,
            description = request.description,
            eventType = request.eventType,
            organizationName = request.organizationName ?: organization?.name,
            locationName = request.locationName,
            latitude = request.latitude,
            longitude = request.longitude,
            eventDate = request.eventDate,
            contactInfo = request.contactInfo
        )

        val savedEvent = eventRepository.save(event)
        return toEventResponse(savedEvent, null)
    }

    @Transactional(readOnly = true)
    fun getEventById(eventId: UUID): EventResponse {
        val event = eventRepository.findById(eventId)
            .orElseThrow { IllegalArgumentException("Event not found") }
        return toEventResponse(event, null)
    }

    @Transactional(readOnly = true)
    fun getUpcomingEvents(): List<EventResponse> {
        return eventRepository.findUpcomingEvents(OffsetDateTime.now())
            .map { toEventResponse(it, null) }
    }

    @Transactional(readOnly = true)
    fun getEventsByOrganization(organizationId: UUID): List<EventResponse> {
        return eventRepository.findByOrganizationIdOrderByEventDateAsc(organizationId)
            .map { toEventResponse(it, null) }
    }

    @Transactional(readOnly = true)
    fun getEventsByType(eventType: String): List<EventResponse> {
        return eventRepository.findByEventTypeAndEventDateGreaterThanEqualOrderByEventDateAsc(
            com.stayrooted.domain.EventType.valueOf(eventType.uppercase()),
            OffsetDateTime.now()
        ).map { toEventResponse(it, null) }
    }

    @Transactional(readOnly = true)
    fun searchEvents(searchTerm: String): List<EventResponse> {
        return eventRepository.searchEvents(searchTerm, OffsetDateTime.now())
            .map { toEventResponse(it, null) }
    }

    @Transactional(readOnly = true)
    fun findNearbyEvents(request: SearchNearbyEventsRequest): List<EventResponse> {
        val radiusMeters = request.radiusKm * 1000 // Convert km to meters

        val events = if (request.eventType != null) {
            eventRepository.findNearbyByType(
                latitude = request.latitude,
                longitude = request.longitude,
                radiusMeters = radiusMeters,
                eventType = request.eventType.name,
                now = OffsetDateTime.now()
            )
        } else {
            eventRepository.findNearby(
                latitude = request.latitude,
                longitude = request.longitude,
                radiusMeters = radiusMeters,
                now = OffsetDateTime.now()
            )
        }

        return events.map { event ->
            val distance = if (event.latitude != null && event.longitude != null) {
                calculateDistance(
                    request.latitude,
                    request.longitude,
                    event.latitude.toDouble(),
                    event.longitude.toDouble()
                )
            } else null

            toEventResponse(event, distance)
        }
    }

    @Transactional
    fun updateEvent(eventId: UUID, userId: UUID, request: UpdateEventRequest): EventResponse {
        val event = eventRepository.findById(eventId)
            .orElseThrow { IllegalArgumentException("Event not found") }

        // Check permission: user must be the creator or org admin/moderator
        if (!canEditEvent(event, userId)) {
            throw IllegalAccessException("You don't have permission to update this event")
        }

        request.title?.let { event.title = it }
        request.description?.let { event.description = it }
        request.eventType?.let { event.eventType = it }
        request.locationName?.let { event.locationName = it }
        request.latitude?.let { event.latitude = it }
        request.longitude?.let { event.longitude = it }
        request.eventDate?.let { event.eventDate = it }
        request.contactInfo?.let { event.contactInfo = it }

        val updatedEvent = eventRepository.save(event)
        return toEventResponse(updatedEvent, null)
    }

    @Transactional
    fun deleteEvent(eventId: UUID, userId: UUID) {
        val event = eventRepository.findById(eventId)
            .orElseThrow { IllegalArgumentException("Event not found") }

        // Check permission
        if (!canEditEvent(event, userId)) {
            throw IllegalAccessException("You don't have permission to delete this event")
        }

        eventRepository.delete(event)
    }

    @Transactional(readOnly = true)
    fun getMyCreatedEvents(userId: UUID): List<EventResponse> {
        return eventRepository.findByCreatedByIdOrderByEventDateDesc(userId)
            .map { toEventResponse(it, null) }
    }

    // Helper methods
    private fun canEditEvent(event: Event, userId: UUID): Boolean {
        // Creator can edit
        if (event.createdBy?.id == userId) return true

        // Organization admin/moderator can edit
        event.organization?.let { org ->
            val membership = organizationMemberRepository.findByOrganizationIdAndUserId(org.id!!, userId)
            if (membership.isPresent && membership.get().role.name in listOf("ADMIN", "MODERATOR")) {
                return true
            }
        }

        return false
    }

    private fun toEventResponse(event: Event, distanceKm: Double?): EventResponse {
        return EventResponse(
            id = event.id!!,
            organizationId = event.organization?.id,
            organizationName = event.organizationName ?: event.organization?.name,
            organizationLogoUrl = event.organization?.logoUrl,
            createdByUserId = event.createdBy?.id,
            createdByUsername = event.createdBy?.username,
            title = event.title,
            description = event.description,
            eventType = event.eventType,
            locationName = event.locationName,
            latitude = event.latitude,
            longitude = event.longitude,
            eventDate = event.eventDate,
            contactInfo = event.contactInfo,
            distanceKm = distanceKm,
            createdAt = event.createdAt!!
        )
    }

    // Calculate distance between two coordinates using Haversine formula
    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadiusKm = 6371.0

        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return earthRadiusKm * c
    }
}
