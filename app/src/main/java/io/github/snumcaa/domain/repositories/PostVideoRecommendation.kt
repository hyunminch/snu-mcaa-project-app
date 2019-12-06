package io.github.snumcaa.domain.repositories

import com.google.gson.annotations.SerializedName

data class PostVideoRecommendation(
        @SerializedName("video") val video: String,
        @SerializedName("text") val text: String?
)
