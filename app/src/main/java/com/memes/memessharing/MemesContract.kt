package com.memes.memessharing

import com.memes.memessharing.api.MemeListResponse
import com.memes.memessharing.api.MemeVideosListResponse


interface MemesContract {
    interface MemeView {
        fun showListView(memesData: List<MemeListResponse.MemesData>)
        fun showFilteredVideoViewPager(
            memeListData: List<MemeListResponse.MemesData>,
            memesData: List<MemeVideosListResponse>
        )

        fun hideListView()
        fun showLoadingView()
        fun hideLoadingView()
        fun showErrorView()
        fun showEmptyListView()
        fun showHomeView(memeListResponse: List<MemeVideosListResponse>)
        fun showProgressBar()
        fun hideProgressBar()
    }

    interface Presenter {
        fun callMemeListApi()
        fun callMemeVideoApi()
    }
}
