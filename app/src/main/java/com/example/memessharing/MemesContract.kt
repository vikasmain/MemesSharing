package com.example.memessharing

interface MemesContract {
    interface MemeView {
        fun showListView()
        fun showHomeView()
    }

    interface Presenter {
        fun handleMemeApi()
        fun showHomePage()
    }
}
