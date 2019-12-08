package io.github.snumcaa.domain.repositories

import com.google.gson.annotations.SerializedName

data class YouTubeVideoRecommendation(
        @SerializedName("id") val id: Long,
        @SerializedName("username") val posterUsername: String,
        @SerializedName("video") val videoId: String,
        @SerializedName("text") val text: String?,
        @SerializedName("shared") val shared: Boolean
)