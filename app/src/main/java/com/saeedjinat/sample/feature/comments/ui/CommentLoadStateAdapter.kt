package com.saeedjinat.sample.feature.comments.ui

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.saeedjinat.sample.core.ui.NetworkStateItemViewHolder

/**
 * @author : Saeed Jinat
 * created at : 2022 - 06 - 10
 */
class CommentLoadStateAdapter(private val adapter: CommentsAdapter) :
    LoadStateAdapter<NetworkStateItemViewHolder>() {

    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) {
        holder.bindTo(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NetworkStateItemViewHolder {
        return NetworkStateItemViewHolder(parent) { adapter.retry() }
    }
}