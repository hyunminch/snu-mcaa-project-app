package io.github.snumcaa.domain.repositories

import com.google.gson.annotations.SerializedName

data class ProfileUpdate(@SerializedName("profile") val profile: String)
