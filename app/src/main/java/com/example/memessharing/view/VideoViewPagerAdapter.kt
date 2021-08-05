package com.example.memessharing.view

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.memessharing.R
import com.example.memessharing.api.MemeListResponse
import com.google.android.exoplayer2.SimpleExoPlayer
import javax.inject.Inject

class VideoViewPagerAdapter @Inject constructor(
    private val context: Context,
    private val simpleExoPlayer: SimpleExoPlayer
) :
    RecyclerView.Adapter<VideoViewPagerHolder>() {
    private val videosList = mutableListOf<MemeListResponse.MemesData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewPagerHolder {
        val view = View.inflate(context, R.layout.home_view_pager_item, null) as ViewGroup
        return VideoViewPagerHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewPagerHolder, position: Int) {
        holder.bindData(videosList[position])
    }

    override fun getItemCount(): Int {
        return videosList.size
    }
}

class VideoViewPagerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindData(memesData: MemeListResponse.MemesData) {

    }
}