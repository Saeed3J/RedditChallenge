package com.saeedjinat.sample.core.network

import android.util.Log
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author : Saeed Jinat
 * created at : 2022 - 06 - 10
 */
class RetrofitClient {

    companion object {
        private const val TAG = "RedditApi"
        private const val BASE_URL = "https://www.reddit.com/"
        fun create(): RedditApi {
            val logger = HttpLoggingInterceptor {
                Log.d(TAG, it)
            }
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(HttpUrl.parse(BASE_URL)!!)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RedditApi::class.java)
        }
    }
}