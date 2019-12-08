package io.github.snumcaa.domain.repositories

import com.google.gson.annotations.SerializedName

data class NotificationResponse(
        @SerializedName("video_id") val videoId: String,
        @SerializedName("message") val message: String?,
        @SerializedName("shared_count") val sharedCount: Int
)