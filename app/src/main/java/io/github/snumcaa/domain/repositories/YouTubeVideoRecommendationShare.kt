package io.github.snumcaa.domain.repositories

import com.google.gson.annotations.SerializedName

data class YouTubeVideoRecommendationShare(
        @SerializedName("id") val id: Long,
        @SerializedName("sharer") val sharerId: Long,
        @SerializedName("recommendation") val recommendation: Long
)