package com.severianfw.picto.downloadmanager

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import com.severianfw.picto.R
import javax.inject.Inject

class DownloadManagerHelperImpl @Inject constructor(private val context: Context) : DownloadManagerHelper {

    private val downloadManager: DownloadManager by lazy {
        context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }

    override fun downloadPhoto(photoUrl: String) {

        val request = DownloadManager.Request(Uri.parse(photoUrl))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle(context.getString(R.string.download_title))
            .setDescription(context.getString(R.string.download_description))
        downloadManager.enqueue(request)
    }
}