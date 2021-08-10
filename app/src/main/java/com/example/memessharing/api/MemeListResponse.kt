package com.example.memessharing.api

import com.google.gson.annotations.SerializedName

data class MemeListResponse(
    @SerializedName("data") val data: List<MemesData>
) {
    data class MemesData(
        @SerializedName("title") val title: String,
        @SerializedName("description") val description: String,
        @SerializedName("subtitle") val subTitle: String,
        @SerializedName("imageurl") val imageUrl: String,
        @SerializedName("videourl") val videoUrl: String
    )
}
