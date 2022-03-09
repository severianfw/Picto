package com.severianfw.picto.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.severianfw.picto.databinding.ActivityDetailBinding
import com.severianfw.picto.domain.model.PhotoItemModel

class DetailActivity : AppCompatActivity() {

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG = "detail_activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setPhotoData()

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setPhotoData() {
        val image = intent.getParcelableExtra<PhotoItemModel>(TAG)
        if (image != null) {
            binding.apply {
                Glide.with(this@DetailActivity)
                    .load(image.mainImageUri)
                    .into(ivPhoto)
                if (image.description.isNotBlank()) {
                    tvDesc.text = image.description
                }
                tvUsername.text = image.authorName
                Glide.with(this@DetailActivity)
                    .load(image.profilePictureUri)
                    .circleCrop()
                    .into(ivUserProfilePicture)
            }
        }
    }
}