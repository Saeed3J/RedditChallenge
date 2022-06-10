package com.saeedjinat.sample.feature.posts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.saeedjinat.sample.feature.posts.api.PostRepository
import com.saeedjinat.sample.core.network.ServiceManager
import com.saeedjinat.sample.core.paging.wrapPageLoadStates
import com.saeedjinat.sample.core.ui.GlideApp
import com.saeedjinat.sample.databinding.ActivityPostsBinding
import com.saeedjinat.sample.feature.posts.ui.PostsAdapter
import com.saeedjinat.sample.feature.posts.ui.PostsLoadStateAdapter
import com.saeedjinat.sample.feature.posts.ui.PostListingViewModel
import kotlinx.coroutines.flow.*

class PostsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostsBinding

    private lateinit var adapter: PostsAdapter

    private val model: PostListingViewModel by lazy {
        val repo = ServiceManager.getInstance().getRedditPostRepository()
        PostListingViewModel(repo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAdapter()
        initSwipeToRefresh()
    }

    private fun initSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener(adapter::refresh)
    }

    private fun initAdapter() {
        val glide = GlideApp.with(this@PostsActivity)
        adapter = PostsAdapter(glide)

        binding.rvList.adapter = adapter.withLoadStateHeaderAndFooter(
            header = PostsLoadStateAdapter(adapter),
            footer = PostsLoadStateAdapter(adapter)
        )

        lifecycleScope.launchWhenCreated {
            model.posts.collectLatest {
                adapter.submitData(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collect { loadStates ->
                binding.swipeRefresh.isRefreshing =
                    loadStates.mediator?.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                .wrapPageLoadStates()
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.rvList.scrollToPosition(0) }
        }

        val topicOrdinal = intent.getIntExtra(KEY_REDDIT_TOPIC, 0)
        val topicType = PostRepository.TopicType.values()[topicOrdinal]
        model.showTopicPosts(topicType.getTopicKey())
    }

    companion object {
        const val KEY_REDDIT_TOPIC = "reddit_topic"
        fun intentFor(context: Context, topicType: PostRepository.TopicType): Intent {
            val intent = Intent(context, PostsActivity::class.java)
            intent.putExtra(KEY_REDDIT_TOPIC, topicType.ordinal)
            return intent
        }
    }

}
