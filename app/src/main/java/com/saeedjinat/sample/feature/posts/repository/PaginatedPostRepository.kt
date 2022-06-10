package com.saeedjinat.sample.feature.posts.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.saeedjinat.sample.feature.posts.paging.PostPagingSource
import com.saeedjinat.sample.core.network.RedditApi
import com.saeedjinat.sample.feature.posts.api.PostRepository

/**
 * @author : Saeed Jinat
 * created at : 2022 - 06 - 09
 */
class PaginatedPostRepository(
    private val redditApi: RedditApi
) :
    PostRepository {
    override fun getPostsPerTopic(topicKey: String, pageSize: Int) =
        Pager(
            PagingConfig(pageSize)
        ) {
            PostPagingSource(
                redditApi = redditApi,
                topicKey = topicKey
            )
        }.flow
}