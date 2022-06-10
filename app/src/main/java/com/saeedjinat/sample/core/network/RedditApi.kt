package com.saeedjinat.sample.core.network

import com.saeedjinat.sample.core.dto.CommentResponse
import com.saeedjinat.sample.core.dto.PostResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * @author : Saeed Jinat
 * created at : 2022 - 06 - 09
 */

interface RedditApi {

    @GET("{permalink}.json")
    suspend fun getComments(
        @Path("permalink", encoded = true) permalink: String,
        @Query("limit") limit: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null
    ): List<CommentResponse>


    @GET("/r/all/{topic}.json")
    suspend fun getTopicListing(
        @Path("topic") topic: String,
        @Query("limit") limit: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null
    ): PostResponse

}