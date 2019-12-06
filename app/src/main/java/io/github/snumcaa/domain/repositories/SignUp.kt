package io.github.snumcaa.domain.repositories

import com.google.gson.annotations.SerializedName

data class SignUp(
        @SerializedName("username") val username: String,
        @SerializedName("password") val password: String
)