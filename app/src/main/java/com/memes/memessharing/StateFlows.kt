package com.memes.memessharing

<<<<<<< HEAD:app/src/main/java/com/example/memessharing/StateFlows.kt
import com.example.memessharing.api.MemeListResponse
=======
import com.memes.memessharing.api.MemeListResponse
>>>>>>> Create release:app/src/main/java/com/memes/memessharing/StateFlows.kt
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.MutableStateFlow

@ActivityScoped
object StateFlows {

    internal val clickListenerStateFlow =
        MutableStateFlow<Pair<MemeListResponse.MemesData?, List<MemeListResponse.MemesData>>?>(
            null
        )
<<<<<<< HEAD

    internal val progressStateFlow = MutableStateFlow(false)
=======
    internal val progressBarStateFlow = MutableStateFlow(false)
>>>>>>> Create View implementation and removed activity reference from module
}