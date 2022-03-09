package com.severianfw.picto.view.detail

import android.app.DownloadManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.severianfw.picto.databinding.ActivityDetailBinding
import com.severianfw.picto.domain.model.PhotoItemModel
import java.util.*

class DetailActivity : AppCompatActivity() {

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG = "detail_activity"
    }

    private lateinit var downloadManager: DownloadManager
    var downloadId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val photoItem = intent.getParcelableExtra<PhotoItemModel>(TAG)
        setPhotoData(photoItem)

        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnDownload.setOnClickListener {
            initializeDownloadManager()
            downloadPhoto(photoItem)
        }
    }

    private fun setPhotoData(photoItem: PhotoItemModel?) {
        if (photoItem != null) {
            binding.apply {
                Glide.with(this@DetailActivity)
                    .load(photoItem.mainImageUri)
                    .into(ivPhoto)
                if (photoItem.description.isNotBlank()) {
                    tvDesc.text = photoItem.description
                }
                tvUsername.text = photoItem.authorName
                Glide.with(this@DetailActivity)
                    .load(photoItem.profilePictureUri)
                    .circleCrop()
                    .into(ivUserProfilePicture)
            }
        }
    }

    private fun initializeDownloadManager() {
        downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
    }

    private fun requestDownloadManager(photoItem: PhotoItemModel?): DownloadManager.Request? {
        if (photoItem != null) {
            val url = photoItem.mainImageUri
            var fileName = "Unsplash Photo"
            if (photoItem.description.isNotBlank()) {
                fileName = photoItem.description
            }
            fileName =
                fileName.substring(0, 1).uppercase(Locale.getDefault()) + fileName.substring(1)

            return DownloadManager.Request(Uri.parse(url))
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setTitle(fileName)
                .setDescription("Photo is Downloading..")
        }
        return null
    }

    private fun downloadPhoto(photoItem: PhotoItemModel?) {
        downloadId = downloadManager.enqueue(requestDownloadManager(photoItem))
    }
}