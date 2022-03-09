package com.severianfw.picto.downloadmanager

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import javax.inject.Inject

class DownloadManagerHelperImpl @Inject constructor(context: Context) : DownloadManagerHelper {

    private val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    override fun downloadPhoto(photoUrl: String) {

        val request = DownloadManager.Request(Uri.parse(photoUrl))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle("Unsplash Photo")
            .setDescription("Photo is Downloading..")
        downloadManager.enqueue(request)
    }
}