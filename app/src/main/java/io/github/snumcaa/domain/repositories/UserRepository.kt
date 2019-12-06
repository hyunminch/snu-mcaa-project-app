package io.github.snumcaa.domain.repositories

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserRepository {
    @POST("api/users/profile")
    suspend fun updateProfile(@Body update: ProfileUpdate): Profile

    @GET("api/users/profile")
    suspend fun getProfile(): Profile

    @GET("api/users/profile/public")
    suspend fun getPublicProfile(@Query("id") id: String): Profile

    @POST("api/users/signup")
    suspend fun signUp(@Body signUp: SignUp)

    @POST("api/users/signin")
    suspend fun signIn()

    @POST("api/users/follow")
    suspend fun follow(@Body followed: Followed)

    @GET("api/users/following")
    suspend fun getFollowing(): List<Followed>

    @GET("api/users/followers")
    suspend fun getFollowers(): List<Following>
}
