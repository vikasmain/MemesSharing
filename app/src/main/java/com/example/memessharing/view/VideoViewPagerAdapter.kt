package com.example.memessharing.view

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.example.memessharing.R
import com.example.memessharing.api.MemeVideosListResponse
import androidx.appcompat.content.res.AppCompatResources
import com.example.memessharing.StateFlows
import com.example.memessharing.api.MemeListResponse
import com.example.memessharing.databinding.HomeViewPagerItemBinding
import com.example.memessharing.helper.DownloadVideoHelper
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class VideoViewPagerAdapter(
    private val context: Context,
    private val downloadVideoHelper: DownloadVideoHelper
) :
    RecyclerView.Adapter<VideoViewPagerHolder>() {
    private val videosList = mutableListOf<MemeVideosListResponse.MemesData>()
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
            HomeViewPagerItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return VideoViewPagerHolder(binding, context, downloadVideoHelper)
    }

    override fun onBindViewHolder(holder: VideoViewPagerHolder, position: Int) {
        holder.bindData(videosList[position])
    }

    override fun getItemCount(): Int {
        return videosList.size
    }
}

class VideoViewPagerHolder(
    private val binding: HomeViewPagerItemBinding,
    private val context: Context,
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
            handleLikeButtonClick()
            handleShareButtonClick(context, memesData)
        }
    }

    private fun HomeViewPagerItemBinding.handleLikeButtonClick() {
        likeButton.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(context, R.anim.bounce_animation)
            likeButton.startAnimation(animation)
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
        context: Context,
        memesData: MemeVideosListResponse.MemesData
    ) {
        shareButton.setOnClickListener {
            mediaPlayer?.pause()
            StateFlows.progressStateFlow.value = true
            downloadVideoHelper.handleVideoDownload(context, memesData.videoUrl, memesData.title)
        }
    }

    private fun playMediaPlayer() {
        with(binding.playPauseButton) {
            setImageDrawable(
                AppCompatResources.getDrawable(
                    context,
                    R.drawable.pause_button
                )
            )
            postDelayed(
                {
                    visibility = View.GONE
                },
                2000
            )
            mediaPlayer?.start()
        }
    }

    private fun pauseMediaPlayer() {
        with(binding.playPauseButton) {
            setImageDrawable(
                AppCompatResources.getDrawable(
                    context,
                    R.drawable.play_button
                )
            )
            val animation = AnimationUtils.loadAnimation(context, R.anim.bounce_animation)
            startAnimation(animation)
            visibility = View.VISIBLE
            mediaPlayer?.pause()
        }
    }
}
