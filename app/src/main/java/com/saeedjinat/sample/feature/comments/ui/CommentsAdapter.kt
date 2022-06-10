package com.saeedjinat.sample.feature.comments.ui

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.saeedjinat.sample.feature.comments.dto.CommentModel

/**
 * @author : Saeed Jinat
 * created at : 2022 - 06 - 10
 */
class CommentsAdapter() : PagingDataAdapter<CommentModel, CommentViewHolder>(COMMENT_COMPARATOR) {

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder.create(parent)
    }

    companion object {
        val COMMENT_COMPARATOR = object : DiffUtil.ItemCallback<CommentModel>() {
            override fun areItemsTheSame(oldItem: CommentModel, newItem: CommentModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: CommentModel,
                newItem: CommentModel,
            ): Boolean {
                return oldItem.body == newItem.body
            }
        }
    }
}