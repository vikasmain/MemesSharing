package com.memes.memessharing.view

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.content.res.AppCompatResources
import com.memes.memessharing.R
import com.memes.memessharing.StateFlows
import com.memes.memessharing.api.MemeListResponse
import com.memes.memessharing.api.MemeVideosListResponse
import com.memes.memessharing.databinding.HomeViewPagerItemBinding
import com.memes.memessharing.helper.DownloadVideoHelper
import dagger.hilt.android.scopes.ActivityScoped

@ActivityScoped
class VideoViewPagerAdapter(
    private val context: Context,
    private val downloadVideoHelper: DownloadVideoHelper
) :
    RecyclerView.Adapter<VideoViewPagerHolder>() {
    private val videosList = mutableListOf<MemeVideosListResponse>()
    internal fun updateData(memeListResponse: List<MemeVideosListResponse>) {
        videosList.clear()
        videosList.addAll(memeListResponse)
        notifyDataSetChanged()
    }

    internal fun filterMemeData(
        memeListData: List<MemeListResponse.MemesData>,
        memesData: List<MemeVideosListResponse>
    ) {
        videosList.clear()
        memeListData.forEach {
            val memeVideoData = memesData.find { it.subTitle == it.subTitle }
            if (it.subTitle == memeVideoData?.subTitle) {
                videosList.add(
                    MemeVideosListResponse(
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
        memesData: MemeVideosListResponse
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
        memesData: MemeVideosListResponse,
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
        memesData: MemeVideosListResponse
    ) {
        shareButton.setOnClickListener {
            mediaPlayer?.pause()
<<<<<<< HEAD
            StateFlows.progressStateFlow.value = true
=======
            StateFlows.progressBarStateFlow.value = true
>>>>>>> Create View implementation and removed activity reference from module
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
<<<<<<< HEAD
            postDelayed(
                {
                    visibility = View.GONE
                },
=======
            binding.playPauseButton.postDelayed(
                { binding.playPauseButton.visibility = View.GONE },
>>>>>>> Create View implementation and removed activity reference from module
                2000
            )
            binding.playPauseButton.visibility = View.VISIBLE
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
