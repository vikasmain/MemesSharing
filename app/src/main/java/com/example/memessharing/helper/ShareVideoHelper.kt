package com.example.memessharing.helper

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import dagger.hilt.android.scopes.ActivityScoped
import java.io.File
import javax.inject.Inject

@ActivityScoped
class ShareVideoHelper @Inject constructor(private val activity: Activity) {

    internal fun shareDownloadedVideo(downloadedVideoUri: Uri) {
        if (ContentResolver.SCHEME_FILE == downloadedVideoUri.scheme) {
            val file = downloadedVideoUri.path?.let {
                File(it)
            }
            file?.let {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "*/*"
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "Enjoying MemeShare app? Download the app from playStore."
                    )
                    putExtra(
                        Intent.EXTRA_STREAM, FileProvider.getUriForFile(
                            activity, activity.applicationContext.packageName + ".provider",
                            file
                        )
                    )
                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                }
                activity.startActivity(
                    Intent.createChooser(
                        intent,
                        "Share this Video"
                    )
                )
            }
        }
    }
}
