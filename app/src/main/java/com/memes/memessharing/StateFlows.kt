package com.memes.memessharing

import com.memes.memessharing.api.MemeListResponse
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.MutableStateFlow

@ActivityScoped
object StateFlows {

    internal val clickListenerStateFlow =
        MutableStateFlow<Pair<MemeListResponse.MemesData?, List<MemeListResponse.MemesData>>?>(
            null
        )

    internal val progressBarStateFlow = MutableStateFlow(false)
}