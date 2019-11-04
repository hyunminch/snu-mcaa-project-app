package io.github.snumcaa.domain.repositories

import retrofit2.http.GET

interface YouTubeVideoRepository {
    @GET("api/videos/recommendation/")
    suspend fun getVideoRecommendations(): List<YouTubeVideoRecommendationResponse>
}
