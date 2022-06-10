package com.saeedjinat.sample.core.paging

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.scan

/**
 * @author : Saeed Jinat
 * created at : 2022 - 06 - 09
 */

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
fun Flow<CombinedLoadStates>.wrapPageLoadStates(): Flow<LoadStates> {
    val syncRemoteState = LoadStatesWrapper()
    return scan(syncRemoteState.toLoadStates()) { _, combinedLoadStates ->
        syncRemoteState.updateFromCombinedLoadStates(combinedLoadStates)
        syncRemoteState.toLoadStates()
    }
}

class LoadStatesWrapper {

    private var refresh: LoadState = LoadState.NotLoading(endOfPaginationReached = false)
    private var prepend: LoadState = LoadState.NotLoading(endOfPaginationReached = false)
    private var append: LoadState = LoadState.NotLoading(endOfPaginationReached = false)
    private var refreshState: WrappedState = WrappedState.NOT_LOADING
    private var prependState: WrappedState = WrappedState.NOT_LOADING
    private var appendState: WrappedState = WrappedState.NOT_LOADING

    private enum class WrappedState {
        NOT_LOADING, REMOTE_STARTED, REMOTE_ERROR, SOURCE_LOADING, SOURCE_ERROR,
    }

    fun toLoadStates() = LoadStates(refresh, prepend, append)

    fun updateFromCombinedLoadStates(combinedLoadStates: CombinedLoadStates) {
        // compute refresh
        computeNext(combinedLoadStates.source.refresh, combinedLoadStates.source.refresh,
            combinedLoadStates.mediator?.refresh,
            refreshState
        ).also {
            refresh = it.first
            refreshState = it.second
        }

        // compute prepend
        computeNext(
            combinedLoadStates.source.refresh, combinedLoadStates.source.prepend,
            combinedLoadStates.mediator?.prepend, prependState,
        ).also {
            prepend = it.first
            prependState = it.second
        }

        // compute append
        computeNext(
            combinedLoadStates.source.refresh,
            combinedLoadStates.source.append,
            combinedLoadStates.mediator?.append,
            appendState,
        ).also {
            append = it.first
            appendState = it.second
        }
    }

    private fun computeNext(
        sourceRefreshState: LoadState,
        sourceState: LoadState,
        remoteState: LoadState?,
        currentWrappedState: WrappedState,
    ): Pair<LoadState, WrappedState> {
        // idle case
        if (remoteState == null) return sourceState to WrappedState.NOT_LOADING

        return when (currentWrappedState) {

            WrappedState.NOT_LOADING -> when (remoteState) {
                // if curr state is idle -> check page state to see if remote is started or error
                is LoadState.Loading -> LoadState.Loading to WrappedState.REMOTE_STARTED
                else -> remoteState to WrappedState.REMOTE_ERROR
            }
            // remote started from started !
            WrappedState.REMOTE_STARTED -> remoteState to WrappedState.REMOTE_ERROR
            // update remote , else remote is error
            WrappedState.REMOTE_ERROR -> when (remoteState) {
                else -> remoteState to WrappedState.REMOTE_ERROR
            }
            // source started from started!
            WrappedState.SOURCE_LOADING -> sourceRefreshState to WrappedState.SOURCE_ERROR
            // update source , else remote is error
            WrappedState.SOURCE_ERROR -> when (sourceRefreshState) {
                else -> sourceRefreshState to WrappedState.SOURCE_ERROR
            }
        }
    }
}