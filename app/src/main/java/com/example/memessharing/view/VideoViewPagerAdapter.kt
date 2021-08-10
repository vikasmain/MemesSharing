package com.example.memessharing.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.memessharing.R
import com.example.memessharing.api.MemeListResponse
import com.example.memessharing.databinding.HomeViewPagerItemBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import kotlinx.android.synthetic.main.home_view_pager_item.view.*
import javax.inject.Inject

class VideoViewPagerAdapter(
    private val context: Context,
    private val simpleExoPlayer: SimpleExoPlayer
) :
    RecyclerView.Adapter<VideoViewPagerHolder>() {
    private val videosList = mutableListOf<MemeListResponse.MemesData>()
    internal fun updateData(memeListResponse: List<MemeListResponse.MemesData>) {
        videosList.clear()
        videosList.addAll(memeListResponse)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewPagerHolder {
        val viewGroup = LayoutInflater.from(context)
            .inflate(R.layout.home_view_pager_item, parent, false) as ViewGroup
        return VideoViewPagerHolder(viewGroup, simpleExoPlayer)
    }

    override fun onBindViewHolder(holder: VideoViewPagerHolder, position: Int) {
        holder.bindData(videosList[position])
    }

    override fun getItemCount(): Int {
        return videosList.size
    }
}

class VideoViewPagerHolder(
    itemView: ViewGroup,
    private val simpleExoPlayer: SimpleExoPlayer
) : RecyclerView.ViewHolder(itemView) {
    fun bindData(memesData: MemeListResponse.MemesData) {
        itemView.exoPlayerView.player = simpleExoPlayer
        val mediaItem = MediaItem.fromUri(memesData.videoUrl)
        simpleExoPlayer.setMediaItem(mediaItem)
        simpleExoPlayer.playWhenReady = true
        simpleExoPlayer.seekTo(0, 0L)
        simpleExoPlayer.prepare()
    }
}
