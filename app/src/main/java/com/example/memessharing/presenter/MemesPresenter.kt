package com.example.memessharing.presenter

import android.net.Uri
import android.util.Log
import android.widget.Toast
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
        observeListViewItemState()
    }

    override fun handleMemeApi() {
        memesModel.getMemesList()
            .onStart {
            }
            .onEach {
                view.showListView(it.data)
            }
            .onCompletion {

            }
            .catch {

            }.launchIn(scope = scope)
    }

    override fun showHomePage() {
        memesModel.getMemeVideosList()
            .onStart {
                view.showLoadingView()
            }
            .onEach {
                view.showHomeView(it)
            }
            .onCompletion {
                view.hideLoadingView()
            }
            .catch {
                throw  it
            }.launchIn(scope = scope)
    }

    private fun showFilteredVideoList(memeListResponse: List<MemeListResponse.MemesData>) {
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

    private fun observeVideoShareState() {
        downloadVideoHelper.videoDownloadStateFlow.asStateFlow().onEach {
            if (it != null) {
                view.hideProgressBar()
                shareVideoHelper.shareDownloadedVideo(Uri.parse(it))
            }
        }.catch {
            view.hideProgressBar()
            Log.e("MainActivity", "error observing Share state flow", it)
            throw it
        }.launchIn(scope)
    }

    private fun observeListViewItemState() {
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
