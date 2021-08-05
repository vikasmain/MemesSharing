package com.example.memessharing.presenter

import com.example.memessharing.MemesContract
import com.example.memessharing.model.MemesModel
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@ActivityScoped
class MemesPresenter @Inject constructor(
    private val memesModel: MemesModel,
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

            }
            .flowOn(Dispatchers.IO)
    }

    override fun showHomePage() {
        memesModel.getMemesList()
            .onStart {
            }
            .onEach {
                view.showListView()
            }
            .onCompletion {

            }
            .catch {

            }
            .flowOn(Dispatchers.IO)
    }
}
