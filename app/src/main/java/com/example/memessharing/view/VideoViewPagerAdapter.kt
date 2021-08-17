package com.example.memessharing.view

import android.app.Activity
import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.example.memessharing.R
import com.example.memessharing.api.MemeVideosListResponse
import androidx.appcompat.content.res.AppCompatResources
import com.example.memessharing.api.MemeListResponse
import com.example.memessharing.databinding.HomeViewPagerItemBinding
import com.example.memessharing.helper.DownloadVideoHelper
import com.google.gson.annotations.SerializedName
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.ServiceScoped
import javax.inject.Inject

@ActivityScoped
class VideoViewPagerAdapter @Inject constructor(
    private val activity: Activity,
    private val downloadVideoHelper: DownloadVideoHelper
) :
    RecyclerView.Adapter<VideoViewPagerHolder>() {
    private val videosList = mutableListOf<MemeVideosListResponse.MemesData>()
    private var videoViewHolder: VideoViewPagerHolder? = null
    internal fun updateData(memeListResponse: List<MemeVideosListResponse.MemesData>) {
        videosList.clear()
        videosList.addAll(memeListResponse)
        notifyDataSetChanged()
    }

    internal fun filterMemeData(
        memeListData: List<MemeListResponse.MemesData>,
        memesData: List<MemeVideosListResponse.MemesData>
    ) {
        videosList.clear()
        memeListData.forEach {
            val memeVideoData = memesData.find { it.subTitle == it.subTitle }
            if (it.subTitle == memeVideoData?.subTitle) {
                videosList.add(
                    MemeVideosListResponse.MemesData(
                        title = memeVideoData.title,
                        subTitle = memeVideoData.subTitle,
                        description = memeVideoData.description,
                        imageUrl = memeVideoData.imageUrl,
                        videoUrl = memeVideoData.videoUrl
                    )
                )
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewPagerHolder {
        val binding =
            HomeViewPagerItemBinding.inflate(LayoutInflater.from(activity), parent, false)
        videoViewHolder = VideoViewPagerHolder(binding, activity, downloadVideoHelper)
        return VideoViewPagerHolder(binding, activity, downloadVideoHelper)
    }

    override fun onBindViewHolder(holder: VideoViewPagerHolder, position: Int) {
        holder.bindData(videosList[position])
    }

    override fun getItemCount(): Int {
        return videosList.size
    }

    internal fun pauseMediaPlayer() {
        videoViewHolder?.pauseMediaPlayer()
    }

    internal fun releaseMediaPlayer() {
        videoViewHolder?.mediaPlayer?.release()
        videoViewHolder?.mediaPlayer?.stop()
    }

    internal fun startMediaPlayer() {
        videoViewHolder?.playMediaPlayer()
    }
}

class VideoViewPagerHolder(
    private val binding: HomeViewPagerItemBinding,
    private val activity: Activity,
    private val downloadVideoHelper: DownloadVideoHelper
) : RecyclerView.ViewHolder(binding.root) {
    internal var mediaPlayer: MediaPlayer? = null

    fun bindData(
        memesData: MemeVideosListResponse.MemesData
    ) {
        with(binding) {
            progressBar.visibility = View.VISIBLE
            val videoView = videoView
            showVideoView(memesData, videoView)
            itemView.setOnClickListener {
                if (videoView.isPlaying) {
                    pauseMediaPlayer()
                } else {
                    playMediaPlayer()
                }
            }
            handleShareButtonClick(memesData)
        }
    }

    private fun HomeViewPagerItemBinding.showVideoView(
        memesData: MemeVideosListResponse.MemesData,
        videoView: VideoView
    ) {
        val uri: Uri = Uri.parse(memesData.videoUrl)
        videoView.setVideoURI(uri)
        videoView.requestFocus()
        videoView.start()
        videoView.setOnPreparedListener {
            it?.setOnVideoSizeChangedListener { mp, width, height ->
                progressBar.visibility = View.GONE
                mediaPlayer = mp
                //                you can set anchor view or media controllers using below code if required.
                //                val mediaController = MediaController(context)
                //                videoView.setMediaController(mediaController)
                //                mediaController.setAnchorView(videoView)
                mp?.start()
            }
        }
    }

    private fun HomeViewPagerItemBinding.handleShareButtonClick(
        memesData: MemeVideosListResponse.MemesData
    ) {
        shareButton.setOnClickListener {
            downloadVideoHelper.handleVideoDownload(memesData.videoUrl, memesData.title)
        }
    }

    internal fun playMediaPlayer() {
        with(binding) {
            playPauseButton.setImageDrawable(
                AppCompatResources.getDrawable(
                    activity,
                    R.drawable.pause_button
                )
            )
            itemView.postDelayed(
                { playPauseButton.visibility = View.GONE },
                3000
            )
            mediaPlayer?.start()
        }
    }

    internal fun pauseMediaPlayer() {
        with(binding) {
            playPauseButton.setImageDrawable(
                AppCompatResources.getDrawable(
                    activity,
                    R.drawable.play_button
                )
            )
            playPauseButton.visibility = View.VISIBLE
            mediaPlayer?.pause()
        }
    }
}
