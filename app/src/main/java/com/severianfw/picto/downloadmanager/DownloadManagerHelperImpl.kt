package com.severianfw.picto.downloadmanager

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.widget.Toast
import com.severianfw.picto.R
import javax.inject.Inject

class DownloadManagerHelperImpl @Inject constructor(private val context: Context) :
    DownloadManagerHelper {

    private val downloadManager: DownloadManager by lazy {
        context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }

    private val onDownloadCompleted = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Toast.makeText(context, "Download complete", Toast.LENGTH_SHORT).show()
        }
    }

    override fun downloadPhoto(photoUrl: String) {
        context.registerReceiver(
            onDownloadCompleted,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
        val request = DownloadManager.Request(Uri.parse(photoUrl))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle(context.getString(R.string.download_title))
            .setDescription(context.getString(R.string.download_description))
        downloadManager.enqueue(request)
    }
}