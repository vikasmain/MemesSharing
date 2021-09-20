package com.memes.memessharing

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.memes.memessharing.helper.DownloadVideoHelper
import com.memes.memessharing.helper.ShareVideoHelper
import com.memes.memessharing.model.MemesModel
import com.memes.memessharing.presenter.MemesPresenter
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import org.junit.Test
import com.nhaarman.mockitokotlin2.mock
import org.junit.runner.RunWith
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
class MemePresenterTest {

    val shareVideoHelper = mock<ShareVideoHelper>()
    val memesModel = mock<MemesModel>()
    val scope = MainScope() + CoroutineName("MemesCoroutine")
    val view = mock<MemesContract.MemeView>()
    val downloadHelper = mock<DownloadVideoHelper>()
    val activity = mock<AppCompatActivity>()

    val presenter = MemesPresenter(
        downloadVideoHelper = downloadHelper,
        shareVideoHelper = shareVideoHelper,
        memesModel = memesModel,
        scope = scope,
        view = view
    )

    @Test
    fun `when meme list api successful then show grid view`() {
        scope.launch {
            val testData = flow {
                emit(TestMemeData.getMemeListData())
            }
            whenever(memesModel.getMemesList()).thenReturn(testData)
            presenter.callMemeListApi()
            verify(view).showListView(TestMemeData.getMemeListData().data)
        }
    }

    @Test
    fun `when meme video list api successful then show hoem view with view pager adapter`() {
        scope.launch {
            val testData = flow {
                emit(TestMemeData.getMemeVideoListData())
            }
            whenever(memesModel.getMemeVideosList()).thenReturn(testData)
            presenter.callMemeVideoApi()
            verify(view).showLoadingView()
            verify(view).showHomeView(TestMemeData.getMemeVideoListData())
            verify(view).hideLoadingView()
        }
    }

    @Test
    fun `when meme video list api failed then show error view`() {
        scope.launch {
            whenever(memesModel.getMemeVideosList()).thenThrow(NullPointerException("Null pointer exception"))
            presenter.callMemeVideoApi()
            verify(view).showLoadingView()
            verify(view).showErrorView()
            verify(view).hideLoadingView()
        }
    }

    @Test
    fun `when meme video list api returns empty list then show empty home page view`() {
        scope.launch {
            val testData = flow {
                emit(TestMemeData.getEmptyMemeVideoListData())
            }
            whenever(memesModel.getMemeVideosList()).thenReturn(testData)
            presenter.callMemeVideoApi()
            verify(view).showLoadingView()
            verify(view).showEmptyListView()
            verify(view).hideLoadingView()
        }
    }

    @Test
    fun `when video downloading completes after that video is ready to be shareable`() {
        scope.launch {
            presenter.observeVideoShareState()
            verify(view).hideProgressBar()
            verify(shareVideoHelper).shareDownloadedVideo(
                activity,
                Uri.parse("https://www.google.com")
            )
        }
    }

    @Test
    fun `when meme list view item is clicked then call meme video list api and show filtered video list on home page`() {
        scope.launch {
            val testData = flow {
                emit(TestMemeData.getMemeVideoListData())
            }
            whenever(memesModel.getMemeVideosList()).thenReturn(testData)
            presenter.showFilteredVideoList(TestMemeData.getMemeListData().data)
            verify(view).hideListView()
            verify(view).showFilteredVideoViewPager(
                TestMemeData.getMemeListData().data,
                TestMemeData.getMemeVideoListData()
            )
        }
    }
}