package io.github.snumcaa.networking

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BasicAuthClient<T> {
    private val client =  OkHttpClient.Builder()
            .addInterceptor(BasicAuthInterceptor("test_user", "snu_mcaa"))
            .build()

    val gson = GsonBuilder()
            .setLenient()
            .create();

    private val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    fun create(service: Class<T>): T {
        return retrofit.create(service)
    }
}
