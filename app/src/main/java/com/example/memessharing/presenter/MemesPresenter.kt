package com.example.memessharing.presenter

import android.net.Uri
import android.util.Log
import com.example.memessharing.MemesContract
import com.example.memessharing.StateFlows
import com.example.memessharing.api.MemeListResponse
import com.example.memessharing.model.MemesModel
import com.example.memessharing.helper.DownloadVideoHelper
import com.example.memessharing.helper.ShareVideoHelper
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
                if (it.data.isEmpty()) {
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
                view.showFilteredVideoViewPager(memeListResponse, it.data)
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
}
