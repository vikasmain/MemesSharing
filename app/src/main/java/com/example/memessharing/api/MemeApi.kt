package com.example.memessharing.api

import retrofit2.http.GET

interface MemeApi {
    @GET("/items")
    suspend fun fetchMemeVideosList(): MemeVideosListResponse

    @GET("/types")
    suspend fun fetchMemesList(): MemeListResponse
}