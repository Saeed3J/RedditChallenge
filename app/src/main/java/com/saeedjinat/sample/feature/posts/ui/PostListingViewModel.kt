package com.saeedjinat.sample.feature.posts.ui

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.saeedjinat.sample.feature.posts.api.PostRepository
import kotlinx.coroutines.flow.flatMapLatest

/**
 * @author : Saeed Jinat
 * created at : 2022 - 06 - 09
 */
class PostListingViewModel(
    private val repository: PostRepository
) : ViewModel() {

    private val topicLiveData = MutableLiveData<String>()

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val posts = topicLiveData
        .asFlow()
        .flatMapLatest { repository.getPostsPerTopic(it, DEF_PAGE_SIZE) }
        .cachedIn(viewModelScope)

    fun showTopicPosts(topicKey: String) {
        topicLiveData.value = topicKey
    }

    companion object {
        const val DEF_PAGE_SIZE = 20
    }
}