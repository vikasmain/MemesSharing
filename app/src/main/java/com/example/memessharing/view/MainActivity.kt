package com.example.memessharing.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.memessharing.MemesContract
import com.example.memessharing.R
import com.example.memessharing.api.MemeVideosListResponse
import com.example.memessharing.databinding.ActivityMainBinding
import com.example.memessharing.presenter.MemesPresenter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import android.view.WindowManager
import android.os.Build
import android.view.Window
import androidx.recyclerview.widget.GridLayoutManager
import com.example.memessharing.api.MemeListResponse
import com.example.memessharing.helper.DownloadVideoHelper

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MemesContract.MemeView {

    @Inject
    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var memesPresenter: MemesPresenter

    @Inject
    lateinit var downloadVideHelper: DownloadVideoHelper

    @Inject
    lateinit var videoViewPagerAdapter: VideoViewPagerAdapter

    @Inject
    lateinit var memeListViewAdapter: MemeListViewAdapter

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
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.page1 -> {
                    with(binding) {
                        homeView.homeView.visibility = View.VISIBLE
                        listView.listView.visibility = View.GONE
                        memesPresenter.callMemeVideoApi()
                    }
                    true
                }
                R.id.page2 -> {
                    with(binding) {
                        homeView.homeView.visibility = View.GONE
                        listView.listView.visibility = View.VISIBLE
                        memesPresenter.callMemeListApi()
                    }
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    override fun showListView(memesData: List<MemeListResponse.MemesData>) {
        with(binding.listView.recyclerView) {
            visibility = View.VISIBLE
            adapter = memeListViewAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            memeListViewAdapter.updateList(memesData)
        }
    }

    override fun showLoadingView() {
        binding.homeView.progressBar.visibility = View.VISIBLE
    }

    override fun hideLoadingView() {
        with(binding) {
            homeView.progressBar.visibility = View.GONE
            listView.progressBar.visibility = View.GONE
        }
    }

    override fun showErrorView() {

    }

    override fun showEmptyListView() {

    }

    override fun showHomeView(memeListResponse: MemeVideosListResponse) {
        val viewPager = binding.homeView.viewPager
        viewPager.adapter = videoViewPagerAdapter
        videoViewPagerAdapter.updateData(memeListResponse.data)
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
                videoViewPagerAdapter.updateData(memeListResponse.data)
            }
        })
    }

    override fun showFilteredVideoViewPager(
        memeListData: List<MemeListResponse.MemesData>,
        memesData: List<MemeVideosListResponse.MemesData>
    ) {
        hideListView()
        binding.listView.listViewPager2.visibility = View.VISIBLE
        binding.listView.listViewPager2.adapter = videoViewPagerAdapter
        videoViewPagerAdapter.filterMemeData(memeListData, memesData)
        binding.listView.listViewPager2.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
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
                videoViewPagerAdapter.filterMemeData(memeListData, memesData)
            }
        })
    }

    override fun hideListView() {
        with(binding.listView) {
            recyclerView.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }

    override fun onStart() {
        super.onStart()
        memesPresenter.callMemeVideoApi()
        // you can see the benefit of putting this method here when you will come to app after sharing video on another app
    }

    override fun onStop() {
        binding.homeView.viewPager.adapter = null
        super.onStop()
    }

    override fun onDestroy() {
        videoViewPagerAdapter.releaseMediaPlayer()
        super.onDestroy()
    }

    override fun showProgressBar() {
        with(binding.homeView) {
            videoDownloadProgressBar.visibility = View.VISIBLE
            videoDownloadTextView.visibility = View.VISIBLE
        }
    }

    override fun hideProgressBar() {
        with(binding.homeView) {
            videoDownloadProgressBar.visibility = View.GONE
            videoDownloadTextView.visibility = View.GONE
        }
    }
}
