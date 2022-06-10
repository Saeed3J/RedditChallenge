package com.saeedjinat.sample.core.network

import com.saeedjinat.sample.feature.comments.api.PaginatedCommentRepository
import com.saeedjinat.sample.feature.comments.api.CommentsRepository
import com.saeedjinat.sample.feature.posts.api.PostRepository
import com.saeedjinat.sample.feature.posts.repository.PaginatedPostRepository

/**
 * @author : Saeed Jinat
 * created at : 2022 - 06 - 09
 */
interface ServiceManager {

    companion object {
        private val LOCK = Any()
        private var instance: ServiceManager? = null
        fun getInstance(): ServiceManager {
            synchronized(LOCK) {
                if (instance == null) {
                    instance = DefaultServiceManager()
                }
                return instance!!
            }
        }
    }

    fun getRedditPostRepository(): PostRepository
    fun getRedditCommentRepository(): CommentsRepository

    fun getRedditApi(): RedditApi
}

class DefaultServiceManager() : ServiceManager {

    private val api by lazy {
        RetrofitClient.create()
    }

    override fun getRedditCommentRepository(): CommentsRepository {
        return PaginatedCommentRepository(getRedditApi())
    }

    override fun getRedditPostRepository(): PostRepository {
        return PaginatedPostRepository(getRedditApi())
    }

    override fun getRedditApi(): RedditApi {
        return api
    }
}
