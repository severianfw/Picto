package com.severianfw.picto.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.severianfw.picto.data.remote.PhotoResponse
import com.severianfw.picto.databinding.ActivityDetailBinding

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
        val image = intent.getParcelableExtra<PhotoResponse>(TAG)
        if (image != null) {
            binding.apply {
                Glide.with(this@DetailActivity)
                    .load(image.urls?.small)
                    .into(ivPhoto)
                if (image.description != null) {
                    tvDesc.text = image.description
                }
                tvUsername.text = image.user?.name
                Glide.with(this@DetailActivity)
                    .load(image.user?.profileImage?.medium)
                    .circleCrop()
                    .into(ivUserProfilePicture)
            }
        }
    }
}