package com.saeedjinat.sample.core.dto

import com.saeedjinat.sample.feature.comments.dto.CommentModel

/**
 * @author : Saeed Jinat
 * created at : 2022 - 06 - 10
 */

class CommentResponse(val data: CommentData)
class CommentData(
    val children: List<CommentChildren>,
    val after: String?,
    val before: String?
)

data class CommentChildren(val data: CommentModel)
