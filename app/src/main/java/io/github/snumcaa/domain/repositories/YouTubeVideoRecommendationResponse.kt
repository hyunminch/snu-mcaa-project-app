package io.github.snumcaa.domain.repositories

import com.google.gson.annotations.SerializedName

data class YouTubeVideoRecommendationResponse(
        @SerializedName("username") val posterUsername: String,
        @SerializedName("video") val videoId: String,
        @SerializedName("text") val text: String?
)
