package com.example.memessharing

import com.example.memessharing.api.MemeListResponse
import com.example.memessharing.api.MemeVideosListResponse
import com.example.memessharing.api.SubTitle

object TestMemeData {

    fun getMemeListData(): MemeListResponse {
        return MemeListResponse(
            data = listOf(
                MemeListResponse.MemesData(
                    title = "Meme",
                    description = "Meme description",
                    imageUrl = "https://www.google.com",
                    subTitle = SubTitle.MEDIA
                )
            )
        )
    }

    fun getMemeVideoListData(): MemeVideosListResponse {
        return MemeVideosListResponse(
            data = listOf(
                MemeVideosListResponse.MemesData(
                    title = "Meme Video",
                    description = "Meme Description",
                    imageUrl = "https://fitnessgym546.000webhostapp.com/download%20(1).jpeg",
                    videoUrl = "https://fitnessgym546.000webhostapp.com/download6.mp4",
                    subTitle = SubTitle.MEDIA
                )
            )
        )
    }

    fun getEmptyMemeVideoListData(): MemeVideosListResponse {
        return MemeVideosListResponse(
            data = emptyList()
        )
    }
}