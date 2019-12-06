package io.github.snumcaa.domain.repositories

import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("id") val id: String,
        @SerializedName("username") val username: String,
        @SerializedName("following") val following: Boolean
)