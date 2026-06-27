package com.rzhf.mangareader.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.mangadex.org"

    private val headerInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("User-Agent", "DexReader/1.0 (Android; RaihanZhafran)")
            .addHeader("Referer", "https://mangadex.org/")
            .addHeader("Origin", "https://mangadex.org/")
            .build()
        chain.proceed(request)
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val _okHttpClient = OkHttpClient.Builder()
        .addInterceptor(headerInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    fun getOkHttpClient(): OkHttpClient {
        return _okHttpClient
    }

    val instance: MangaDexApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(_okHttpClient) // Gunakan variabel _okHttpClient di sini
            .build()
            .create(MangaDexApi::class.java)
    }
}