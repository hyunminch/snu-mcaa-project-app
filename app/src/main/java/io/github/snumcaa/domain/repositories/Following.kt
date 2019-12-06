package io.github.snumcaa.domain.repositories

import com.google.gson.annotations.SerializedName

data class Following(
        @SerializedName("following") val following: String
)
