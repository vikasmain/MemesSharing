package com.example.memessharing.presenter

import android.util.Log
import com.example.memessharing.MemesContract
import com.example.memessharing.model.MemesModel
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@ActivityScoped
class MemesPresenter @Inject constructor(
    private val memesModel: MemesModel,
    private val scope: CoroutineScope,
    private val view: MemesContract.MemeView
) : MemesContract.Presenter {

    override fun handleMemeApi() {
        memesModel.getMemesList()
            .onStart {
            }
            .onEach {
                view.showListView()
            }
            .onCompletion {

            }
            .catch {

            }.launchIn(scope = scope)
    }

    override fun showHomePage() {
        memesModel.getMemesList()
            .onStart {
            }
            .onEach {
                view.showHomeView(it)
            }
            .onCompletion {

            }
            .catch {
                throw  it
            }
            .launchIn(scope = scope)
    }
}
