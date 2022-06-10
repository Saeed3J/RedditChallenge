package com.saeedjinat.sample.feature.comments.ui

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.saeedjinat.sample.feature.comments.api.CommentsRepository
import kotlinx.coroutines.flow.flatMapLatest

/**
 * @author : Saeed Jinat
 * created at : 2022 - 06 - 10
 */
class CommentListingViewModel(
    private val repository: CommentsRepository
) : ViewModel() {

    private val permalinkLiveData = MutableLiveData<String>()

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val comments = permalinkLiveData
        .asFlow()
        .flatMapLatest { repository.getComments(it, DEF_PAGE_SIZE) }
        .cachedIn(viewModelScope)

    fun showComments(permalink: String) {
        permalinkLiveData.value = permalink
    }

    companion object {
        const val DEF_PAGE_SIZE = 20
    }
}