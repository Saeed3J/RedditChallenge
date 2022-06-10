package com.saeedjinat.sample.feature.comments.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.saeedjinat.sample.feature.comments.dto.CommentModel
import com.saeedjinat.sample.core.network.RedditApi
import retrofit2.HttpException
import java.io.IOException

/**
 * @author : Saeed Jinat
 * created at : 2022 - 06 - 10
 */
class CommentPagingSource(
    private val redditApi: RedditApi,
    private val permalink: String
) : PagingSource<String, CommentModel>() {
    override fun getRefreshKey(state: PagingState<String, CommentModel>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(
                anchorPosition
            )?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, CommentModel> {
        return try {
            val list = redditApi.getComments(
                permalink = permalink,
                after = if (params is LoadParams.Append) params.key else null,
                before = if (params is LoadParams.Prepend) params.key else null,
                limit = params.loadSize
            )

            LoadResult.Page(
                // TODO : get rid of indexing with proper deserialization
                data = list[1].data.children.map { it.data },
                prevKey = list[1].data.before,
                nextKey = list[1].data.after
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}