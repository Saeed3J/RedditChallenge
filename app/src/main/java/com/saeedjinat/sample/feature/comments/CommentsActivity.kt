package com.saeedjinat.sample.feature.comments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.saeedjinat.sample.core.paging.wrapPageLoadStates
import com.saeedjinat.sample.feature.comments.ui.CommentListingViewModel
import com.saeedjinat.sample.feature.comments.ui.CommentLoadStateAdapter
import com.saeedjinat.sample.feature.comments.ui.CommentsAdapter
import com.saeedjinat.sample.core.network.ServiceManager
import com.saeedjinat.sample.databinding.ActivityCommentsBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter

class CommentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommentsBinding
    private lateinit var adapter: CommentsAdapter

    private val model: CommentListingViewModel by lazy {
        CommentListingViewModel(ServiceManager.getInstance().getRedditCommentRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAdapter()
    }

    private fun initAdapter() {

        adapter = CommentsAdapter()
        binding.commentsList.adapter = adapter.withLoadStateHeaderAndFooter(
            header = CommentLoadStateAdapter(adapter),
            footer = CommentLoadStateAdapter(adapter)
        )

        lifecycleScope.launchWhenCreated {
            model.comments.collectLatest {
                adapter.submitData(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                .wrapPageLoadStates()
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.commentsList.scrollToPosition(0) }
        }

        // trigger
        when (val permalink = intent.getStringExtra(KEY_PERMALINK)) {
            is String -> model.showComments(permalink)
        }
    }

    companion object {
        private const val KEY_PERMALINK = "permalink"
        fun intentFor(context: Context, permalink: String): Intent {
            val intent = Intent(context, CommentsActivity::class.java)
            intent.putExtra(KEY_PERMALINK, permalink)
            return intent
        }
    }
}