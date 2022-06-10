package com.saeedjinat.sample.feature.posts.ui

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.saeedjinat.sample.core.ui.GlideRequests
import com.saeedjinat.sample.feature.posts.dto.PostModel

/**
 * @author : Saeed Jinat
 * created at : 2022 - 06 - 09
 */
class PostsAdapter(private val glide: GlideRequests) :
    PagingDataAdapter<PostModel, PostViewHolder>(POST_COMPARATOR) {

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder.create(parent, glide)
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<PostModel>() {

            override fun areContentsTheSame(oldItem: PostModel, newItem: PostModel): Boolean =
                oldItem.title == newItem.title

            override fun areItemsTheSame(oldItem: PostModel, newItem: PostModel): Boolean =
                oldItem.id == newItem.id
        }
    }

}