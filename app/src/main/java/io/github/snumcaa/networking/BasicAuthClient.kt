package io.github.snumcaa.networking

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BasicAuthClient<T> {
    val serverUrl: String = "http://10.0.2.2:8000"
//    val serverUrl: String = "http://35.230.245.253:8000"

    val gson = GsonBuilder()
            .setLenient()
            .create()

    fun createNoAuth(service: Class<T>): T {
        val client =  OkHttpClient.Builder().build()

        val retrofit = Retrofit.Builder()
                .baseUrl(serverUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        return retrofit.create(service)
    }

    fun createTempAuth(service: Class<T>, username: String, password: String): T {
        val client =  OkHttpClient.Builder()
                .addInterceptor(BasicAuthInterceptor(username, password))
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(serverUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        return retrofit.create(service)
    }

    fun createAuth(service: Class<T>, context: Context): T {
        val preferences = context.getSharedPreferences("VIDEOSHAREX_PREFS", MODE_PRIVATE)

        val username = preferences.getString("username", "")
        val password = preferences.getString("password", "")

        if (username is String && password is String) {
            val client =  OkHttpClient.Builder()
                    .addInterceptor(BasicAuthInterceptor(username, password))
                    .build()

            val retrofit = Retrofit.Builder()
                    .baseUrl(serverUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

            return retrofit.create(service)
        } else {
            val client =  OkHttpClient.Builder().build()

            val retrofit = Retrofit.Builder()
                    .baseUrl(serverUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

            return retrofit.create(service)
        }
    }
}
