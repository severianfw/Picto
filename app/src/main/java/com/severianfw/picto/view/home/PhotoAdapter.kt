package com.severianfw.picto.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.severianfw.picto.data.remote.ImageUrl
import com.severianfw.picto.databinding.ItemPhotosBinding

class PhotoAdapter :
    ListAdapter<ImageUrl, PhotoAdapter.ViewHolder>(PhotoItemDiffCallback()) {

    inner class ViewHolder(private val binding: ItemPhotosBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: ImageUrl) {
            Glide.with(binding.root)
                .load(photo.regular)
                .transform(FitCenter(), CenterCrop(), RoundedCorners(32))
                .into(binding.ivPhoto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPhotosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PhotoItemDiffCallback : DiffUtil.ItemCallback<ImageUrl>() {
    override fun areItemsTheSame(oldItem: ImageUrl, newItem: ImageUrl): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: ImageUrl, newItem: ImageUrl): Boolean =
        oldItem == newItem

}