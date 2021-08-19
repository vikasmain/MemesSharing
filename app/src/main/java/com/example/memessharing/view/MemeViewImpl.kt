package com.example.memessharing.view

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.memessharing.MemesContract
import com.example.memessharing.api.MemeListResponse
import com.example.memessharing.api.MemeVideosListResponse
import com.example.memessharing.databinding.ActivityMainBinding
import com.example.memessharing.helper.DownloadVideoHelper
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class MemeViewImpl @Inject constructor(
    private val binding: ActivityMainBinding,
    private val memeListViewAdapter: MemeListViewAdapter,
    private val downloadVideoHelper: DownloadVideoHelper
) : MemesContract.MemeView {
    override fun showListView(memesData: List<MemeListResponse.MemesData>) {
        with(binding.listView.recyclerView) {
            visibility = View.VISIBLE
            adapter = memeListViewAdapter
            layoutManager = GridLayoutManager(context, 2)
            memeListViewAdapter.updateList(memesData)
        }
    }

    override fun showFilteredVideoViewPager(
        memeListData: List<MemeListResponse.MemesData>,
        memesData: List<MemeVideosListResponse.MemesData>
    ) {
        hideListView()
        with(binding.listView.listViewPager2) {
            visibility = View.VISIBLE
            val videoViewPagerAdapter =
                VideoViewPagerAdapter(context, downloadVideoHelper)
            adapter = videoViewPagerAdapter
            videoViewPagerAdapter.filterMemeData(memeListData, memesData)
            registerOnPageChangeCallback(object :
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
    }

    override fun hideListView() {
        with(binding.listView) {
            recyclerView.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
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
        with(binding.homeView.viewPager) {
            val videoViewPagerAdapter = VideoViewPagerAdapter(context, downloadVideoHelper)
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