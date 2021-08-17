package com.example.memessharing

import com.example.memessharing.api.MemeListResponse
import com.example.memessharing.api.MemeVideosListResponse
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.MutableStateFlow

@ActivityScoped
object StateFlows {

    internal val clickListenerStateFlow =
        MutableStateFlow<Pair<MemeListResponse.MemesData?, List<MemeListResponse.MemesData>>?>(
            null
        )
}