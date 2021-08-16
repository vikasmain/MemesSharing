package com.example.memessharing

import com.example.memessharing.api.MemeListResponse

interface MemesContract {
    interface MemeView {
        fun showListView()
        fun showHomeView(memeListResponse: MemeListResponse)
        fun showProgressBar()
        fun hideProgressBar()
    }

    interface Presenter {
        fun handleMemeApi()
        fun showHomePage()
    }
}
