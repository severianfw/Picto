package com.severianfw.picto.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.severianfw.picto.PictoApplication
import com.severianfw.picto.databinding.ActivityDetailBinding
import com.severianfw.picto.domain.model.PhotoItemModel
import com.severianfw.picto.viewmodel.PhotoDetailViewModel
import javax.inject.Inject
import com.severianfw.picto.utils.Constant

class PhotoDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var photoDetailViewModel: PhotoDetailViewModel

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {

        (application as PictoApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val photoItem = intent.getParcelableExtra<PhotoItemModel>(Constant.PHOTO_ITEM)
        setPhotoData(photoItem)
        setBackButtonListener()
        setDownloadButtonListener(photoItem)
    }

    private fun setBackButtonListener() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setDownloadButtonListener(photoItem: PhotoItemModel?) {
        binding.btnDownload.setOnClickListener {
            if (photoItem != null) {
                photoDetailViewModel.downloadPhoto(photoItem.mainImageUri)
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
}
