package com.memes.memessharing.deps

import android.content.Context
import android.view.LayoutInflater
import com.memes.memessharing.MemesContract
import com.memes.memessharing.databinding.ActivityMainBinding
import com.memes.memessharing.view.MemeViewImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.plus

@Module
@InstallIn(ActivityComponent::class)
abstract class MainModule {

    companion object {
        @Provides
        @ActivityScoped
        fun providesMainActivityBinding(@ActivityContext context: Context): ActivityMainBinding {
            return ActivityMainBinding.inflate(LayoutInflater.from(context))
        }

        @Provides
        @ActivityScoped
        fun providesCoroutineScope(): CoroutineScope {
            return MainScope() + CoroutineName("MemesCoroutine")
        }
    }

    @Binds
    @ActivityScoped
    abstract fun providesView(viewImpl: MemeViewImpl): MemesContract.MemeView
}
