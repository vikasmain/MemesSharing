package com.memes.memessharing.api

import retrofit2.http.GET

interface MemeApi {
    @GET("/index.php")
    suspend fun fetchMemeVideosList(): List<MemeVideosListResponse>

    @GET("/types.php")
    suspend fun fetchMemesList(): MemeListResponse
}
