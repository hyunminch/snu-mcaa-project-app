package io.github.snumcaa.domain.repositories

import retrofit2.http.GET
import retrofit2.http.POST

interface YouTubeVideoRepository {
    @GET("api/videos/recommendation")
    suspend fun getVideoRecommendations(): List<YouTubeVideoRecommendation>

    @POST("api/videos/recommendation/share")
    suspend fun shareVideoRecommendation(): YouTubeVideoRecommendationShare
}
