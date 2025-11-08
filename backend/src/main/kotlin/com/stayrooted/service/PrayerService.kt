package com.stayrooted.service

import com.stayrooted.domain.*
import com.stayrooted.dto.*
import com.stayrooted.repository.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime
import java.util.UUID

@Service
class PrayerService(
    private val prayerRepository: PrayerRepository,
    private val prayerFollowerRepository: PrayerFollowerRepository,
    private val prayerUpdateRepository: PrayerUpdateRepository,
    private val userRepository: UserRepository
) {

    @Transactional
    fun createPrayer(userId: UUID, request: CreatePrayerRequest): PrayerResponse {
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User not found") }

        val prayer = Prayer(
            user = user,
            title = request.title,
            content = request.content,
            scriptureReference = request.scriptureReference,
            scriptureText = request.scriptureText,
            youtubeMusicUrl = request.youtubeMusicUrl,
            visibility = request.visibility,
            isShared = request.visibility != PrayerVisibility.PRIVATE
        )

        val savedPrayer = prayerRepository.save(prayer)
        return toPrayerResponse(savedPrayer, userId)
    }

    @Transactional(readOnly = true)
    fun getPrayerById(prayerId: UUID, currentUserId: UUID): PrayerResponse {
        val prayer = prayerRepository.findById(prayerId)
            .orElseThrow { IllegalArgumentException("Prayer not found") }

        // Check access permissions
        if (prayer.visibility == PrayerVisibility.PRIVATE && prayer.user.id != currentUserId) {
            throw IllegalAccessException("You don't have permission to view this prayer")
        }

        return toPrayerResponse(prayer, currentUserId)
    }

    @Transactional(readOnly = true)
    fun getMyPrayers(userId: UUID): List<PrayerResponse> {
        return prayerRepository.findByUserIdOrderByCreatedAtDesc(userId)
            .map { toPrayerResponse(it, userId) }
    }

    @Transactional(readOnly = true)
    fun getCommunityPrayers(currentUserId: UUID): List<PrayerResponse> {
        return prayerRepository.findCommunityAndPublicPrayers()
            .map { toPrayerResponse(it, currentUserId) }
    }

    @Transactional(readOnly = true)
    fun getPraiseReports(currentUserId: UUID): List<PrayerResponse> {
        return prayerRepository.findPraiseReports()
            .map { toPrayerResponse(it, currentUserId) }
    }

    @Transactional(readOnly = true)
    fun getPrayersImFollowing(userId: UUID): List<PrayerResponse> {
        return prayerFollowerRepository.findByUserIdOrderByCreatedAtDesc(userId)
            .map { toPrayerResponse(it.prayer, userId) }
    }

    @Transactional
    fun updatePrayer(prayerId: UUID, userId: UUID, request: UpdatePrayerRequest): PrayerResponse {
        val prayer = prayerRepository.findById(prayerId)
            .orElseThrow { IllegalArgumentException("Prayer not found") }

        // Only the owner can update their prayer
        if (prayer.user.id != userId) {
            throw IllegalAccessException("You don't have permission to update this prayer")
        }

        request.title?.let { prayer.title = it }
        request.content?.let { prayer.content = it }
        request.scriptureReference?.let { prayer.scriptureReference = it }
        request.scriptureText?.let { prayer.scriptureText = it }
        request.youtubeMusicUrl?.let { prayer.youtubeMusicUrl = it }
        request.visibility?.let {
            prayer.visibility = it
            prayer.isShared = it != PrayerVisibility.PRIVATE
        }

        val updatedPrayer = prayerRepository.save(prayer)
        return toPrayerResponse(updatedPrayer, userId)
    }

    @Transactional
    fun deletePrayer(prayerId: UUID, userId: UUID) {
        val prayer = prayerRepository.findById(prayerId)
            .orElseThrow { IllegalArgumentException("Prayer not found") }

        // Only the owner can delete their prayer
        if (prayer.user.id != userId) {
            throw IllegalAccessException("You don't have permission to delete this prayer")
        }

        prayerRepository.delete(prayer)
    }

    @Transactional
    fun markPrayerAsAnswered(prayerId: UUID, userId: UUID, request: MarkPrayerAnsweredRequest): PrayerResponse {
        val prayer = prayerRepository.findById(prayerId)
            .orElseThrow { IllegalArgumentException("Prayer not found") }

        // Only the owner can mark their prayer as answered
        if (prayer.user.id != userId) {
            throw IllegalAccessException("You don't have permission to update this prayer")
        }

        prayer.status = PrayerStatus.ANSWERED
        prayer.answeredAt = OffsetDateTime.now()

        val updatedPrayer = prayerRepository.save(prayer)

        // Create an update with the testimony
        createPrayerUpdate(prayerId, request.testimony)

        return toPrayerResponse(updatedPrayer, userId)
    }

    @Transactional
    fun followPrayer(prayerId: UUID, userId: UUID): PrayerResponse {
        val prayer = prayerRepository.findById(prayerId)
            .orElseThrow { IllegalArgumentException("Prayer not found") }

        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User not found") }

        // Check if already following
        if (prayerFollowerRepository.existsByPrayerIdAndUserId(prayerId, userId)) {
            throw IllegalStateException("You are already following this prayer")
        }

        // Check if prayer is accessible
        if (prayer.visibility == PrayerVisibility.PRIVATE && prayer.user.id != userId) {
            throw IllegalAccessException("You cannot follow a private prayer")
        }

        val follower = PrayerFollower(
            prayer = prayer,
            user = user
        )

        prayerFollowerRepository.save(follower)
        return toPrayerResponse(prayer, userId)
    }

    @Transactional
    fun unfollowPrayer(prayerId: UUID, userId: UUID): PrayerResponse {
        val prayer = prayerRepository.findById(prayerId)
            .orElseThrow { IllegalArgumentException("Prayer not found") }

        val follower = prayerFollowerRepository.findByPrayerIdAndUserId(prayerId, userId)
            .orElseThrow { IllegalArgumentException("You are not following this prayer") }

        prayerFollowerRepository.delete(follower)
        return toPrayerResponse(prayer, userId)
    }

    @Transactional
    fun createPrayerUpdate(prayerId: UUID, content: String): PrayerUpdateResponse {
        val prayer = prayerRepository.findById(prayerId)
            .orElseThrow { IllegalArgumentException("Prayer not found") }

        val update = PrayerUpdate(
            prayer = prayer,
            content = content
        )

        val savedUpdate = prayerUpdateRepository.save(update)
        return toPrayerUpdateResponse(savedUpdate)
    }

    @Transactional
    fun addPrayerUpdate(prayerId: UUID, userId: UUID, request: CreatePrayerUpdateRequest): PrayerUpdateResponse {
        val prayer = prayerRepository.findById(prayerId)
            .orElseThrow { IllegalArgumentException("Prayer not found") }

        // Only the owner can add updates
        if (prayer.user.id != userId) {
            throw IllegalAccessException("You don't have permission to add updates to this prayer")
        }

        return createPrayerUpdate(prayerId, request.content)
    }

    @Transactional(readOnly = true)
    fun getPrayerUpdates(prayerId: UUID, currentUserId: UUID): List<PrayerUpdateResponse> {
        val prayer = prayerRepository.findById(prayerId)
            .orElseThrow { IllegalArgumentException("Prayer not found") }

        // Check access permissions
        if (prayer.visibility == PrayerVisibility.PRIVATE && prayer.user.id != currentUserId) {
            throw IllegalAccessException("You don't have permission to view this prayer's updates")
        }

        return prayerUpdateRepository.findByPrayerIdOrderByCreatedAtAsc(prayerId)
            .map { toPrayerUpdateResponse(it) }
    }

    @Transactional(readOnly = true)
    fun getPrayerFollowers(prayerId: UUID, currentUserId: UUID): List<PrayerFollowerResponse> {
        val prayer = prayerRepository.findById(prayerId)
            .orElseThrow { IllegalArgumentException("Prayer not found") }

        // Check access permissions
        if (prayer.visibility == PrayerVisibility.PRIVATE && prayer.user.id != currentUserId) {
            throw IllegalAccessException("You don't have permission to view this prayer's followers")
        }

        return prayerFollowerRepository.findByPrayerIdOrderByCreatedAtDesc(prayerId)
            .map { toPrayerFollowerResponse(it) }
    }

    @Transactional(readOnly = true)
    fun getPrayerStats(userId: UUID): PrayerStatsResponse {
        val allPrayers = prayerRepository.findByUserIdOrderByCreatedAtDesc(userId)
        val following = prayerFollowerRepository.findByUserIdOrderByCreatedAtDesc(userId)

        return PrayerStatsResponse(
            totalPrayers = allPrayers.size.toLong(),
            pendingPrayers = allPrayers.count { it.status == PrayerStatus.PENDING }.toLong(),
            answeredPrayers = allPrayers.count { it.status == PrayerStatus.ANSWERED }.toLong(),
            prayersFollowing = following.size.toLong()
        )
    }

    // Helper methods
    private fun toPrayerResponse(prayer: Prayer, currentUserId: UUID): PrayerResponse {
        val followerCount = prayerFollowerRepository.countByPrayerId(prayer.id!!)
        val updateCount = prayerUpdateRepository.countByPrayerId(prayer.id)
        val isFollowing = prayerFollowerRepository.existsByPrayerIdAndUserId(prayer.id, currentUserId)

        return PrayerResponse(
            id = prayer.id,
            userId = prayer.user.id!!,
            username = prayer.user.username,
            userFullName = prayer.user.fullName,
            title = prayer.title,
            content = prayer.content,
            scriptureReference = prayer.scriptureReference,
            scriptureText = prayer.scriptureText,
            youtubeMusicUrl = prayer.youtubeMusicUrl,
            status = prayer.status,
            answeredAt = prayer.answeredAt,
            visibility = prayer.visibility,
            isShared = prayer.isShared,
            followerCount = followerCount,
            updateCount = updateCount,
            isFollowing = isFollowing,
            createdAt = prayer.createdAt!!,
            updatedAt = prayer.updatedAt!!
        )
    }

    private fun toPrayerUpdateResponse(update: PrayerUpdate): PrayerUpdateResponse {
        return PrayerUpdateResponse(
            id = update.id!!,
            prayerId = update.prayer.id!!,
            content = update.content,
            createdAt = update.createdAt!!
        )
    }

    private fun toPrayerFollowerResponse(follower: PrayerFollower): PrayerFollowerResponse {
        return PrayerFollowerResponse(
            id = follower.id!!,
            userId = follower.user.id!!,
            username = follower.user.username,
            fullName = follower.user.fullName,
            createdAt = follower.createdAt!!
        )
    }
}
