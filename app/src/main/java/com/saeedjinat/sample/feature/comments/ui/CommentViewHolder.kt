package com.saeedjinat.sample.feature.comments.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.saeedjinat.sample.databinding.RedditCommentItemBinding
import com.saeedjinat.sample.feature.comments.dto.CommentModel

/**
 * @author : Saeed Jinat
 * created at : 2022 - 06 - 10
 */
class CommentViewHolder(binding: RedditCommentItemBinding) : RecyclerView.ViewHolder(binding.root) {

    private var commentBody: TextView = binding.commentBody
    private var commentAuthor: TextView = binding.commentAuthor

    companion object {
        fun create(parent: ViewGroup): CommentViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = RedditCommentItemBinding.inflate(inflater, parent, false)
            return CommentViewHolder(binding)
        }
    }

    fun bind(comment: CommentModel?) {
        commentBody.text = comment?.body
        commentAuthor.text = comment?.author
    }
}