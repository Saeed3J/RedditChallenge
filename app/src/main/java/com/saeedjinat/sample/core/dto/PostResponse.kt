package com.saeedjinat.sample.core.dto

import com.saeedjinat.sample.feature.posts.dto.PostModel

/**
 * @author : Saeed Jinat
 * created at : 2022 - 06 - 10
 */
class PostResponse(val data: PostData)
class PostData(
    val children: List<PostChildren>,
    val after: String?,
    val before: String?,
)

data class PostChildren(val data: PostModel)
