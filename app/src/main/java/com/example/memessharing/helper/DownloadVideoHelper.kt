package com.example.memessharing.helper

import android.app.Activity
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.content.Context
import android.content.Intent
import android.content.Context.DOWNLOAD_SERVICE
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import com.example.memessharing.MemesContract.MemeView
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@ActivityScoped
class DownloadVideoHelper @Inject constructor(
    private val activity: Activity,
    private val memeView: MemeView
) {
    internal val videoDownloadStateFlow = MutableStateFlow<String?>(null)

    fun handleVideoDownload(videoUrl: String, videoTitle: String) {
        memeView.showProgressBar()
        activity.registerReceiver(
            videoDownloadReceiver(), IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE
            )
        )
        val downloadManager = activity.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(Uri.parse(videoUrl))
        request.apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            setTitle("Downloading $videoTitle.mp4")
            setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "/MemesSharing/$videoTitle.mp4"
            )
        }
        downloadManager.enqueue(request)
    }

    private fun videoDownloadReceiver() =
        object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == intent.action) {
                    val videoDownloadedId = intent.getLongExtra(
                        DownloadManager.EXTRA_DOWNLOAD_ID, 0
                    )
                    receiveVideoDownloadStatus(videoDownloadedId, context)
                }
            }
        }

    private fun receiveVideoDownloadStatus(
        downloadId: Long,
        context: Context
    ) {
        val downloadManager = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val query = DownloadManager.Query()
        query.setFilterById(downloadId)
        val cursor: Cursor = downloadManager.query(query)
        if (cursor.moveToFirst()) {
            val downloadedLocalUri =
                cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI))
            when (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
                DownloadManager.STATUS_SUCCESSFUL -> {
                    videoDownloadStateFlow.value = downloadedLocalUri
                }
                DownloadManager.STATUS_FAILED -> {
                    videoDownloadStateFlow.value = null
                    Toast.makeText(context, "Video Downloading Failed, Sorry", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }
        cursor.close()
    }
}
