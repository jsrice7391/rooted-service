package com.stayrooted.controller

import com.stayrooted.dto.*
import com.stayrooted.service.EventService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/events")
class EventController(
    private val eventService: EventService
) {

    @PostMapping
    fun createEvent(
        @Valid @RequestBody request: CreateEventRequest,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<EventResponse>> {
        val userId = UUID.fromString(authentication.name)
        val event = eventService.createEvent(userId, request)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                success = true,
                message = "Event created successfully",
                data = event
            )
        )
    }

    @GetMapping("/{eventId}")
    fun getEventById(@PathVariable eventId: UUID): ResponseEntity<ApiResponse<EventResponse>> {
        val event = eventService.getEventById(eventId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Event retrieved successfully",
                data = event
            )
        )
    }

    @GetMapping
    fun getUpcomingEvents(): ResponseEntity<ApiResponse<List<EventResponse>>> {
        val events = eventService.getUpcomingEvents()
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Upcoming events retrieved successfully",
                data = events
            )
        )
    }

    @GetMapping("/organization/{organizationId}")
    fun getEventsByOrganization(
        @PathVariable organizationId: UUID
    ): ResponseEntity<ApiResponse<List<EventResponse>>> {
        val events = eventService.getEventsByOrganization(organizationId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Organization events retrieved successfully",
                data = events
            )
        )
    }

    @GetMapping("/type/{eventType}")
    fun getEventsByType(@PathVariable eventType: String): ResponseEntity<ApiResponse<List<EventResponse>>> {
        val events = eventService.getEventsByType(eventType)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Events by type retrieved successfully",
                data = events
            )
        )
    }

    @GetMapping("/search")
    fun searchEvents(@RequestParam searchTerm: String): ResponseEntity<ApiResponse<List<EventResponse>>> {
        val events = eventService.searchEvents(searchTerm)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Search results retrieved successfully",
                data = events
            )
        )
    }

    @PostMapping("/nearby")
    fun findNearbyEvents(
        @Valid @RequestBody request: SearchNearbyEventsRequest
    ): ResponseEntity<ApiResponse<List<EventResponse>>> {
        val events = eventService.findNearbyEvents(request)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Nearby events retrieved successfully",
                data = events
            )
        )
    }

    @GetMapping("/my-events")
    fun getMyCreatedEvents(authentication: Authentication): ResponseEntity<ApiResponse<List<EventResponse>>> {
        val userId = UUID.fromString(authentication.name)
        val events = eventService.getMyCreatedEvents(userId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Your created events retrieved successfully",
                data = events
            )
        )
    }

    @PutMapping("/{eventId}")
    fun updateEvent(
        @PathVariable eventId: UUID,
        @Valid @RequestBody request: UpdateEventRequest,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<EventResponse>> {
        val userId = UUID.fromString(authentication.name)
        val event = eventService.updateEvent(eventId, userId, request)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Event updated successfully",
                data = event
            )
        )
    }

    @DeleteMapping("/{eventId}")
    fun deleteEvent(
        @PathVariable eventId: UUID,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<Unit>> {
        val userId = UUID.fromString(authentication.name)
        eventService.deleteEvent(eventId, userId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Event deleted successfully",
                data = Unit
            )
        )
    }
}
