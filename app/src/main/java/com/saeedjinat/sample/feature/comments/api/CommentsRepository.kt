package com.saeedjinat.sample.feature.comments.api

import androidx.paging.PagingData
import com.saeedjinat.sample.feature.comments.dto.CommentModel
import kotlinx.coroutines.flow.Flow

/**
 * @author : Saeed Jinat
 * created at : 2022 - 06 - 10
 */
interface CommentsRepository {
    fun getComments(permalink: String, pageSize: Int): Flow<PagingData<CommentModel>>
}