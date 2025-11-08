package com.stayrooted.controller

import com.stayrooted.dto.*
import com.stayrooted.service.PrayerService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/prayers")
class PrayerController(
    private val prayerService: PrayerService
) {

    @PostMapping
    fun createPrayer(
        @Valid @RequestBody request: CreatePrayerRequest,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<PrayerResponse>> {
        val userId = UUID.fromString(authentication.name)
        val prayer = prayerService.createPrayer(userId, request)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                success = true,
                message = "Prayer created successfully",
                data = prayer
            )
        )
    }

    @GetMapping("/{prayerId}")
    fun getPrayerById(
        @PathVariable prayerId: UUID,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<PrayerResponse>> {
        val userId = UUID.fromString(authentication.name)
        val prayer = prayerService.getPrayerById(prayerId, userId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Prayer retrieved successfully",
                data = prayer
            )
        )
    }

    @GetMapping("/my-prayers")
    fun getMyPrayers(authentication: Authentication): ResponseEntity<ApiResponse<List<PrayerResponse>>> {
        val userId = UUID.fromString(authentication.name)
        val prayers = prayerService.getMyPrayers(userId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Prayers retrieved successfully",
                data = prayers
            )
        )
    }

    @GetMapping("/community")
    fun getCommunityPrayers(authentication: Authentication): ResponseEntity<ApiResponse<List<PrayerResponse>>> {
        val userId = UUID.fromString(authentication.name)
        val prayers = prayerService.getCommunityPrayers(userId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Community prayers retrieved successfully",
                data = prayers
            )
        )
    }

    @GetMapping("/praise-reports")
    fun getPraiseReports(authentication: Authentication): ResponseEntity<ApiResponse<List<PrayerResponse>>> {
        val userId = UUID.fromString(authentication.name)
        val prayers = prayerService.getPraiseReports(userId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Praise reports retrieved successfully",
                data = prayers
            )
        )
    }

    @GetMapping("/following")
    fun getPrayersImFollowing(authentication: Authentication): ResponseEntity<ApiResponse<List<PrayerResponse>>> {
        val userId = UUID.fromString(authentication.name)
        val prayers = prayerService.getPrayersImFollowing(userId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Following prayers retrieved successfully",
                data = prayers
            )
        )
    }

    @PutMapping("/{prayerId}")
    fun updatePrayer(
        @PathVariable prayerId: UUID,
        @Valid @RequestBody request: UpdatePrayerRequest,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<PrayerResponse>> {
        val userId = UUID.fromString(authentication.name)
        val prayer = prayerService.updatePrayer(prayerId, userId, request)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Prayer updated successfully",
                data = prayer
            )
        )
    }

    @DeleteMapping("/{prayerId}")
    fun deletePrayer(
        @PathVariable prayerId: UUID,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<Unit>> {
        val userId = UUID.fromString(authentication.name)
        prayerService.deletePrayer(prayerId, userId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Prayer deleted successfully",
                data = Unit
            )
        )
    }

    @PostMapping("/{prayerId}/mark-answered")
    fun markPrayerAsAnswered(
        @PathVariable prayerId: UUID,
        @Valid @RequestBody request: MarkPrayerAnsweredRequest,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<PrayerResponse>> {
        val userId = UUID.fromString(authentication.name)
        val prayer = prayerService.markPrayerAsAnswered(prayerId, userId, request)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Prayer marked as answered",
                data = prayer
            )
        )
    }

    @PostMapping("/{prayerId}/follow")
    fun followPrayer(
        @PathVariable prayerId: UUID,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<PrayerResponse>> {
        val userId = UUID.fromString(authentication.name)
        val prayer = prayerService.followPrayer(prayerId, userId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Successfully following prayer",
                data = prayer
            )
        )
    }

    @DeleteMapping("/{prayerId}/follow")
    fun unfollowPrayer(
        @PathVariable prayerId: UUID,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<PrayerResponse>> {
        val userId = UUID.fromString(authentication.name)
        val prayer = prayerService.unfollowPrayer(prayerId, userId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Successfully unfollowed prayer",
                data = prayer
            )
        )
    }

    @PostMapping("/{prayerId}/updates")
    fun addPrayerUpdate(
        @PathVariable prayerId: UUID,
        @Valid @RequestBody request: CreatePrayerUpdateRequest,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<PrayerUpdateResponse>> {
        val userId = UUID.fromString(authentication.name)
        val update = prayerService.addPrayerUpdate(prayerId, userId, request)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                success = true,
                message = "Prayer update added successfully",
                data = update
            )
        )
    }

    @GetMapping("/{prayerId}/updates")
    fun getPrayerUpdates(
        @PathVariable prayerId: UUID,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<List<PrayerUpdateResponse>>> {
        val userId = UUID.fromString(authentication.name)
        val updates = prayerService.getPrayerUpdates(prayerId, userId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Prayer updates retrieved successfully",
                data = updates
            )
        )
    }

    @GetMapping("/{prayerId}/followers")
    fun getPrayerFollowers(
        @PathVariable prayerId: UUID,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<List<PrayerFollowerResponse>>> {
        val userId = UUID.fromString(authentication.name)
        val followers = prayerService.getPrayerFollowers(prayerId, userId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Prayer followers retrieved successfully",
                data = followers
            )
        )
    }

    @GetMapping("/stats")
    fun getPrayerStats(authentication: Authentication): ResponseEntity<ApiResponse<PrayerStatsResponse>> {
        val userId = UUID.fromString(authentication.name)
        val stats = prayerService.getPrayerStats(userId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Prayer stats retrieved successfully",
                data = stats
            )
        )
    }
}
