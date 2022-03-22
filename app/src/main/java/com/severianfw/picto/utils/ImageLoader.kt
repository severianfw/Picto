package com.severianfw.picto.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageButton
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.severianfw.picto.R
import com.severianfw.picto.databinding.ActivityDetailBinding
import com.severianfw.picto.databinding.ItemPhotosBinding
import com.severianfw.picto.view.home.PhotoAdapter

object ImageLoader {

    fun loadUrlToImageView(binding: ItemPhotosBinding, imageUrl: String) {
        val requestOptions = RequestOptions().placeholder(R.drawable.ic_default_image)
        Glide.with(binding.root)
            .load(imageUrl)
            .transform(FitCenter(), CenterCrop(), RoundedCorners(PhotoAdapter.CORNERS_RADIUS))
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(requestOptions)
            .into(binding.ivPhoto)
    }

    fun loadDrawableToImageButton(context: Context, drawableId: Int, imageButton: ImageButton) {
        Glide.with(context).load(drawableId).into(imageButton)
    }
}