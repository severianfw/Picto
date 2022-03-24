package com.severianfw.picto.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.severianfw.picto.R
import com.severianfw.picto.view.home.PhotoAdapter

object ImageLoader {

    fun loadUrlToImageView(context: Context, imageUrl: String, imageView: ImageView) {
        val requestOptions = RequestOptions().placeholder(R.drawable.ic_default_image)
        Glide.with(context)
            .load(imageUrl)
            .transform(FitCenter(), CenterCrop(), RoundedCorners(PhotoAdapter.CORNERS_RADIUS))
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(requestOptions)
            .into(imageView)
    }
}