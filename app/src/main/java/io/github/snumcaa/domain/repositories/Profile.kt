package io.github.snumcaa.domain.repositories

import com.google.gson.annotations.SerializedName

data class Profile(
        @SerializedName("user") val user: String,
        @SerializedName("profile") val profile: String
)
