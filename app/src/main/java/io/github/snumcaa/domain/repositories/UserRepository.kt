package io.github.snumcaa.domain.repositories

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserRepository {
    @POST("api/users/profile")
    suspend fun updateProfile(@Body update: ProfileUpdate): Profile

    @GET("api/users/profile")
    suspend fun getProfile(): Profile

    @POST("api/users/signup")
    suspend fun signUp(@Body signUp: SignUp)

    @POST("api/users/signin")
    suspend fun signIn()

    @POST("api/users/follow")
    suspend fun follow(@Body followed: Followed)
}
