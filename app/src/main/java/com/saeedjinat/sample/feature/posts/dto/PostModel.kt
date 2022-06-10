package com.saeedjinat.sample.feature.posts.dto

/**
 * @author : Saeed Jinat
 * created at : 2022 - 06 - 09
 */
data class PostModel(
    val id: String?,
    val title: String,
    val thumbnail: String?,
    val permalink: String?
) {
}