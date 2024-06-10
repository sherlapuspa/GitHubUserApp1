package com.dicoding.githubuserapp.data.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val authIntercept = Interceptor { chain ->
        val request = chain.request()
        val reqHead = request.newBuilder()
            .addHeader("Authorization", "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").build()
        chain.proceed(reqHead)
    }
    val client = OkHttpClient.Builder().addInterceptor(authIntercept).build()

    private const val BASE_URL = "https://api.github.com/"

    val retro =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .client(client).build()

    val apiInstance = retro.create(ApiService::class.java)
}