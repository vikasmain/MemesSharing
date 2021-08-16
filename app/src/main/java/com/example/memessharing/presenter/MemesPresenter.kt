package com.example.memessharing.presenter

import android.net.Uri
import android.util.Log
import com.example.memessharing.MemesContract
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
    }

    override fun handleMemeApi() {
        memesModel.getMemesList()
            .onStart {
            }
            .onEach {
                view.showListView()
            }
            .onCompletion {

            }
            .catch {

            }.launchIn(scope = scope)
    }

    override fun showHomePage() {
        memesModel.getMemesList()
            .onStart {
            }
            .onEach {
                view.showHomeView(it)
            }
            .onCompletion {

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
            Log.e("MainActivity", "error observing Share state flow", it)
            throw it
        }.launchIn(scope)
    }
}
