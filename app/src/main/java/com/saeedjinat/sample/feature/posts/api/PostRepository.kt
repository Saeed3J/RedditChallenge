package com.saeedjinat.sample.feature.posts.api

import androidx.paging.PagingData
import com.saeedjinat.sample.feature.posts.dto.PostModel
import kotlinx.coroutines.flow.Flow

/**
 * @author : Saeed Jinat
 * created at : 2022 - 06 - 09
 */
interface PostRepository {
    fun getPostsPerTopic(topicKey: String, pageSize: Int): Flow<PagingData<PostModel>>


    enum class TopicType {
        NEW {
            override fun getTopicKey(): String = "new"
        },
        TOP {
            override fun getTopicKey(): String = "top"
        },
        HOT {
            override fun getTopicKey(): String = "hot"
        };

        abstract fun getTopicKey(): String
    }
}