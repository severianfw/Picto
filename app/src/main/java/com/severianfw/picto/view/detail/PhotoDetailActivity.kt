package com.severianfw.picto.view.detail

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.severianfw.picto.PictoApplication
import com.severianfw.picto.R
import com.severianfw.picto.databinding.ActivityDetailBinding
import com.severianfw.picto.domain.model.PhotoItemModel
import com.severianfw.picto.viewmodel.PhotoDetailViewModel
import javax.inject.Inject
import com.severianfw.picto.utils.Constant
import com.severianfw.picto.utils.DarkModeUtil
import com.severianfw.picto.utils.ImageLoader

class PhotoDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var photoDetailViewModel: PhotoDetailViewModel

    @Inject
    lateinit var darkModeUtil: DarkModeUtil

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {

        (application as PictoApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupIconColor()

        val photoItem = intent.getParcelableExtra<PhotoItemModel>(Constant.PHOTO_ITEM)
        setPhotoData(photoItem)
        setBackButtonListener()
        setDownloadButtonListener(photoItem)
        setupDownloadReceiver()
    }

    private fun setupIconColor() {
        if (darkModeUtil.isDarkModeActive()) {
            ImageLoader.loadDrawableToImageButton(
                this,
                R.drawable.ic_back_dark,
                binding.btnBack
            )
            ImageLoader.loadDrawableToImageButton(
                this,
                R.drawable.ic_download_dark,
                binding.btnDownload
            )
        } else {
            ImageLoader.loadDrawableToImageButton(
                this,
                R.drawable.ic_back_light,
                binding.btnBack
            )
            ImageLoader.loadDrawableToImageButton(
                this,
                R.drawable.ic_download_light,
                binding.btnDownload
            )
        }
    }

    private fun setBackButtonListener() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private val onDownloadCompleted = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Toast.makeText(context, getString(R.string.download_finish), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupDownloadReceiver() {
        registerReceiver(
            onDownloadCompleted,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
    }

    private fun setDownloadButtonListener(photoItem: PhotoItemModel?) {
        binding.btnDownload.setOnClickListener {
            if (photoItem != null) {
                photoDetailViewModel.downloadPhoto(photoItem.mainImageUri)
                Toast.makeText(this, getString(R.string.download_start), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setPhotoData(photoItem: PhotoItemModel?) {
        if (photoItem != null) {
            with(binding) {
                Glide.with(this@PhotoDetailActivity)
                    .load(photoItem.mainImageUri)
                    .into(ivPhoto)
                if (photoItem.description.isNotBlank()) {
                    tvDesc.text = photoItem.description
                }
                tvUsername.text = photoItem.authorName
                Glide.with(this@PhotoDetailActivity)
                    .load(photoItem.profilePictureUri)
                    .circleCrop()
                    .into(ivUserProfilePicture)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(onDownloadCompleted)
    }
}
