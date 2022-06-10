package com.saeedjinat.sample.feature.posts.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.saeedjinat.sample.R
import com.saeedjinat.sample.core.ui.GlideRequests
import com.saeedjinat.sample.databinding.RedditPostItemBinding
import com.saeedjinat.sample.feature.comments.CommentsActivity
import com.saeedjinat.sample.feature.posts.dto.PostModel

/**
 * @author : Saeed Jinat
 * created at : 2022 - 06 - 09
 */
class PostViewHolder(binding: RedditPostItemBinding, private val glide: GlideRequests) :
    RecyclerView.ViewHolder(binding.root) {

    private val title: TextView = binding.title
    private val thumbnail: ImageView = binding.thumbnail
    private var post: PostModel? = null

    init {
        binding.root.setOnClickListener {
            post?.permalink?.let {
                val context = binding.root.context
                val intent = CommentsActivity.intentFor(context, it)
                context.startActivity(intent)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup, glide: GlideRequests): PostViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = RedditPostItemBinding.inflate(inflater, parent, false)
            return PostViewHolder(binding, glide)
        }
    }

    fun bind(post: PostModel?) {
        this.post = post
        title.text = post?.title
        if (post?.thumbnail?.startsWith("http") == true) {
            thumbnail.visibility = View.VISIBLE
            glide.load(post.thumbnail)
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_article_24)
                .into(thumbnail)
        } else {
            thumbnail.visibility = View.GONE
            glide.clear(thumbnail)
        }
    }
}