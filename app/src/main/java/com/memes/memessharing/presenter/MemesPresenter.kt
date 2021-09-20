package com.memes.memessharing.presenter

import android.net.Uri
import android.util.Log
import com.memes.memessharing.MemesContract
import com.memes.memessharing.StateFlows
import com.memes.memessharing.api.MemeListResponse
import com.memes.memessharing.helper.DownloadVideoHelper
import com.memes.memessharing.helper.ShareVideoHelper
import com.memes.memessharing.model.MemesModel
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@ActivityScoped
class MemesPresenter @Inject constructor(
    private val memesModel: MemesModel,
    private val scope: CoroutineScope,
    private val view: MemesContract.MemeView,
    private val downloadVideoHelper: DownloadVideoHelper,
    private val shareVideoHelper: ShareVideoHelper
) : MemesContract.Presenter {

    internal fun attach() {
        observeVideoShareState()
        observeListViewItemClickState()
<<<<<<< HEAD
        observeProgressBarStateFlow()
=======
        observeProgressBarState()
>>>>>>> Create View implementation and removed activity reference from module
    }

    override fun callMemeListApi() {
        memesModel.getMemesList()
            .onStart {
            }
            .onEach {
                view.showListView(it.data)
            }
            .catch {
                throw it
            }.launchIn(scope = scope)
    }

    override fun callMemeVideoApi() {
        memesModel.getMemeVideosList()
            .onStart {
                view.showLoadingView()
            }
            .onEach {
                if (it.isEmpty()) {
                    view.showEmptyListView()
                } else {
                    view.showHomeView(it)
                }
            }
            .onCompletion {
                view.hideLoadingView()
            }
            .catch {
                view.showErrorView()
                throw  it
            }.launchIn(scope = scope)
    }

    internal fun showFilteredVideoList(memeListResponse: List<MemeListResponse.MemesData>) {
        memesModel.getMemeVideosList()
            .onStart {
                view.hideListView()
                view.showLoadingView()
            }
            .onEach {
                view.showFilteredVideoViewPager(memeListResponse, it)
            }
            .onCompletion {
                view.hideLoadingView()
            }
            .catch {
                throw  it
            }.launchIn(scope = scope)
    }

    internal fun observeVideoShareState() {
        downloadVideoHelper.videoDownloadStateFlow.asStateFlow().onEach {
            if (it != null) {
                view.hideProgressBar()
                shareVideoHelper.shareDownloadedVideo(it.first, Uri.parse(it.second))
            }
        }.catch {
            view.hideProgressBar()
            Log.e("MainActivity", "error observing Share state flow", it)
            throw it
        }.launchIn(scope)
    }

    private fun observeListViewItemClickState() {
        StateFlows.clickListenerStateFlow.asStateFlow().onEach {
            if (it != null) {
                showFilteredVideoList(it.second)
            }
        }.catch {
            Log.e("MainActivity", "error observing Share state flow", it)
            throw it
        }.launchIn(scope)
    }

<<<<<<< HEAD
    private fun observeProgressBarStateFlow() {
        StateFlows.progressStateFlow.asStateFlow().onEach {
=======
    private fun observeProgressBarState() {
        StateFlows.progressBarStateFlow.asStateFlow().onEach {
>>>>>>> Create View implementation and removed activity reference from module
            if (it) {
                view.showProgressBar()
            }
        }.catch {
            Log.e("MainActivity", "error observing progress bar state flow", it)
            throw it
        }.launchIn(scope)
    }
}
