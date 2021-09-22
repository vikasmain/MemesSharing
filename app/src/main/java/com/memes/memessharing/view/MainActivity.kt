package com.memes.memessharing.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import android.view.WindowManager
import android.os.Build
import android.view.Window
import com.memes.memessharing.R
import com.memes.memessharing.StateFlows
import com.memes.memessharing.databinding.ActivityMainBinding
import com.memes.memessharing.presenter.MemesPresenter

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var memesPresenter: MemesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        memesPresenter.attach()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        with(binding) {
            bottomNavigation.setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.page1 -> {
                        homeView.homeView.visibility = View.VISIBLE
                        listView.listView.visibility = View.GONE
                        memesPresenter.callMemeVideoApi()
                        true
                    }
                    R.id.page2 -> {
                        homeView.homeView.visibility = View.GONE
                        listView.listView.visibility = View.VISIBLE
                        memesPresenter.callMemeListApi()
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        memesPresenter.callMemeVideoApi()
        // you can see the benefit of putting this method here when you will come to app after sharing video on another app
    }

    override fun onStop() {
        StateFlows.clickListenerStateFlow.value = null
        StateFlows.progressBarStateFlow.value = false
        binding.homeView.viewPager.adapter = null
        StateFlows.clickListenerStateFlow.value = null
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
