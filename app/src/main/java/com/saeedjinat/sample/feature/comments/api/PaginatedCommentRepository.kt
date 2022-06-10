package com.saeedjinat.sample.feature.comments.api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.saeedjinat.sample.feature.comments.dto.CommentModel
import com.saeedjinat.sample.feature.comments.paging.CommentPagingSource
import com.saeedjinat.sample.core.network.RedditApi
import kotlinx.coroutines.flow.Flow

/**
 * @author : Saeed Jinat
 * created at : 2022 - 06 - 10
 */
class PaginatedCommentRepository(private val redditApi: RedditApi) : CommentsRepository {
    override fun getComments(permalink: String, pageSize: Int): Flow<PagingData<CommentModel>> {
        return Pager(
            PagingConfig(pageSize)
        ) {
            CommentPagingSource(
                redditApi = redditApi,
                permalink = permalink
            )
        }.flow
    }
}