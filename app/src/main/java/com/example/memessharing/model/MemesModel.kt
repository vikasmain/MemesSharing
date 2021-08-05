package com.example.memessharing.model

import com.example.memessharing.api.MemeApi
import com.example.memessharing.api.MemeListResponse
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Dispatcher
import javax.inject.Inject

@ActivityScoped
class MemesModel @Inject constructor(private val memesApi: MemeApi) {
    fun getMemesList(): Flow<MemeListResponse> {
        return flow {
            emit(memesApi.fetchMemesList())
        }.flowOn(Dispatchers.IO)
    }
}
