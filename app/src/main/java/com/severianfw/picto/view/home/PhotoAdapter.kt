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
import com.severianfw.picto.data.remote.PhotoResponse
import com.severianfw.picto.databinding.ItemPhotosBinding

class PhotoAdapter :
    ListAdapter<PhotoResponse, PhotoAdapter.ViewHolder>(PhotoItemDiffCallback()) {

    private lateinit var onItemClickListener: OnItemClickListener

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
        getItem(position).urls?.let { holder.bind(it) }
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(getItem(position))
        }
    }

    interface OnItemClickListener {
        fun onItemClick(photo: PhotoResponse)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

}

class PhotoItemDiffCallback : DiffUtil.ItemCallback<PhotoResponse>() {
    override fun areItemsTheSame(oldItem: PhotoResponse, newItem: PhotoResponse): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: PhotoResponse, newItem: PhotoResponse): Boolean =
        oldItem == newItem

}