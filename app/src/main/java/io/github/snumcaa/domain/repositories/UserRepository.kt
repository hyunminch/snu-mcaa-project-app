package io.github.snumcaa.domain.repositories

import retrofit2.http.Body
import retrofit2.http.POST

interface UserRepository {
    @POST("api/users/follow")
    suspend fun follow(@Body followed: Followed)
}
