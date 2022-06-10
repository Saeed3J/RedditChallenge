package com.saeedjinat.sample.feature.posts.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.saeedjinat.sample.core.network.RedditApi
import com.saeedjinat.sample.feature.posts.dto.PostModel
import retrofit2.HttpException
import java.io.IOException

/**
 * @author : Saeed Jinat
 * created at : 2022 - 06 - 09
 */
class PostPagingSource(
    private val redditApi: RedditApi,
    private val topicKey: String
) : PagingSource<String, PostModel>() {

    override fun getRefreshKey(state: PagingState<String, PostModel>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(
                anchorPosition
            )?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, PostModel> {
        return try {
            val data = redditApi.getTopicListing(
                topic = topicKey,
                after = if (params is LoadParams.Append) params.key else null,
                before = if (params is LoadParams.Prepend) params.key else null,
                limit = params.loadSize
            ).data

            LoadResult.Page(
                data = data.children.map { it.data },
                prevKey = data.before,
                nextKey = data.after
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

}