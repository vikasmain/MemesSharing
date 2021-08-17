package com.example.memessharing

import com.example.memessharing.api.MemeListResponse
import com.example.memessharing.api.MemeVideosListResponse

interface MemesContract {
    interface MemeView {
        fun showListView(memesData: List<MemeListResponse.MemesData>)
        fun showFilteredVideoViewPager(
            memeListData: List<MemeListResponse.MemesData>,
            memesData: List<MemeVideosListResponse.MemesData>
        )

        fun hideListView()
        fun showLoadingView()
        fun hideLoadingView()
        fun showHomeView(memeListResponse: MemeVideosListResponse)
        fun showProgressBar()
        fun hideProgressBar()
    }

    interface Presenter {
        fun handleMemeApi()
        fun showHomePage()
    }
}
