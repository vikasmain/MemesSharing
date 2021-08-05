package com.example.memessharing.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.memessharing.MemesContract
import com.example.memessharing.R
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
        binding.bottomNavigation.setOnItemReselectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.page1 -> {
                    showHomeView()
                }
                R.id.page2 -> {
                    memesPresenter.handleMemeApi()
                }
            }
        }
    }

    override fun showListView() {

    }

    override fun showHomeView() {
        val viewPager = ViewPager2(this)
        val viewPagerAdapter = VideoViewPagerAdapter(this, simpleExoPlayer)
        viewPager.adapter = viewPagerAdapter
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
            }
        })
    }
}
