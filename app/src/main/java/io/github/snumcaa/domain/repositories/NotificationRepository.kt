package io.github.snumcaa.domain.repositories

import retrofit2.http.GET

interface NotificationRepository {

    @GET("api/videos/recommendation/notifications")
    suspend fun getNotifications(): List<NotificationResponse>
}