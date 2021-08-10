package com.example.memessharing.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.memessharing.MemesContract
import com.example.memessharing.R
import com.example.memessharing.api.MemeListResponse
import com.example.memessharing.databinding.ActivityMainBinding
import com.example.memessharing.presenter.MemesPresenter
import com.google.android.exoplayer2.SimpleExoPlayer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MemesContract.MemeView {

    @Inject
    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var memesPresenter: MemesPresenter

    @Inject
    lateinit var simpleExoPlayer: SimpleExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        memesPresenter.showHomePage()
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.page1 -> {
                    binding.homeView.homeView.visibility = View.VISIBLE
                    binding.listView.listView.visibility = View.GONE
                    memesPresenter.showHomePage()
                    true
                }
                R.id.page2 -> {
                    simpleExoPlayer.stop()
                    binding.homeView.homeView.visibility = View.GONE
                    binding.listView.listView.visibility = View.VISIBLE
                    memesPresenter.handleMemeApi()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    override fun showListView() {
    }

    override fun showHomeView(memeListResponse: MemeListResponse) {
        val viewPager = binding.homeView.viewPager
        val viewPagerAdapter =
            VideoViewPagerAdapter(this, simpleExoPlayer)
        viewPager.adapter = viewPagerAdapter
        viewPagerAdapter.updateData(memeListResponse.data)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)

            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                simpleExoPlayer.playWhenReady = true
                viewPagerAdapter.updateData(memeListResponse.data)
                simpleExoPlayer.stop()
            }
        })
    }

    override fun onStop() {
        super.onStop()
        simpleExoPlayer.playWhenReady = false
        simpleExoPlayer.stop()
        simpleExoPlayer.release()
    }
    override fun onDestroy() {
        super.onDestroy()
    }
}
