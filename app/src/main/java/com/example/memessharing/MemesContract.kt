package com.example.memessharing

import com.example.memessharing.api.MemeListResponse

interface MemesContract {
    interface MemeView {
        fun showListView()
        fun showHomeView(memeListResponse: MemeListResponse)
    }

    interface Presenter {
        fun handleMemeApi()
        fun showHomePage()
    }
}
