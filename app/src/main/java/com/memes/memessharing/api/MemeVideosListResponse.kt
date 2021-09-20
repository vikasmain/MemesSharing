package com.memes.memessharing.api

import com.google.gson.annotations.SerializedName

data class MemeVideosListResponse(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("subtitle") val subTitle: SubTitle,
    @SerializedName("imageurl") val imageUrl: String,
    @SerializedName("videourl") val videoUrl: String
)

enum class SubTitle(type: String) {
    @SerializedName("Media")
    MEDIA("Media"),

    @SerializedName("Barber")
    BARBER("Barber"),

    @SerializedName("Hooman")
    HOOMAN("Hooman"),

    @SerializedName("Shampoo")
    SHAMPOO("Shampoo"),

    @SerializedName("Khabi")
    KHABI("Khabi"),

    @SerializedName("Iphone")
    IPHONE("Iphone")
}
