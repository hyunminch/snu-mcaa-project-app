package io.github.snumcaa.domain.repositories

import com.google.gson.annotations.SerializedName

data class YouTubeVideoRecommendationShare(
        @SerializedName("id") val id: String,
        @SerializedName("sharer") val sharer: String,
        @SerializedName("recommendation") val recommendation: String
)
