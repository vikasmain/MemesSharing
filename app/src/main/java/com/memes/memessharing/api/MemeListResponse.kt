package com.memes.memessharing.api

import com.google.gson.annotations.SerializedName

data class MemeListResponse(
    @SerializedName("data") val data: List<MemesData>
) {
    data class MemesData(
        @SerializedName("title") val title: String,
        @SerializedName("description") val description: String,
        @SerializedName("subtitle") val subTitle: SubTitle,
        @SerializedName("imageurl") val imageUrl: String
    )
}
