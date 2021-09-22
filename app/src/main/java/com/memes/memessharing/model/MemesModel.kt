package com.memes.memessharing.model

import com.memes.memessharing.api.MemeApi
import com.memes.memessharing.api.MemeListResponse
import com.memes.memessharing.api.MemeVideosListResponse
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityScoped
class MemesModel @Inject constructor(private val memesApi: MemeApi) {
    fun getMemeVideosList(): Flow<List<MemeVideosListResponse>> {
        return flow {
            emit(memesApi.fetchMemeVideosList())
        }.flowOn(Dispatchers.IO)
    }

    fun getMemesList(): Flow<MemeListResponse> {
        return flow {
            emit(memesApi.fetchMemesList())
        }.flowOn(Dispatchers.IO)
    }
}
