package com.memes.memessharing

import com.memes.memessharing.api.MemeListResponse
import com.memes.memessharing.api.MemeVideosListResponse
import com.memes.memessharing.api.SubTitle


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

    fun getMemeVideoListData(): List<MemeVideosListResponse> {
        return listOf(
            MemeVideosListResponse(
                title = "Meme Video",
                description = "Meme Description",
                imageUrl = "https://fitnessgym546.000webhostapp.com/download%20(1).jpeg",
                videoUrl = "https://fitnessgym546.000webhostapp.com/download6.mp4",
                subTitle = SubTitle.MEDIA
            )
        )
    }

    fun getEmptyMemeVideoListData(): List<MemeVideosListResponse> {
        return emptyList<MemeVideosListResponse>()
    }
}