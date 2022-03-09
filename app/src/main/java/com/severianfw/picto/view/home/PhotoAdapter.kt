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
import com.severianfw.picto.databinding.ItemPhotosBinding
import com.severianfw.picto.domain.model.PhotoItemModel

class PhotoAdapter :
    ListAdapter<PhotoItemModel, PhotoAdapter.ViewHolder>(PhotoItemDiffCallback()) {

    private lateinit var onItemClickListener: OnItemClickListener


    companion object {
        const val CORNERS_RADIUS = 32
    }

    inner class ViewHolder(private val binding: ItemPhotosBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: PhotoItemModel) {
            Glide.with(binding.root)
                .load(photo.thumbnailImageUri)
                .transform(FitCenter(), CenterCrop(), RoundedCorners(CORNERS_RADIUS))
                .into(binding.ivPhoto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPhotosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
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

class PhotoItemDiffCallback : DiffUtil.ItemCallback<PhotoItemModel>() {
    override fun areItemsTheSame(oldItem: PhotoItemModel, newItem: PhotoItemModel): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: PhotoItemModel, newItem: PhotoItemModel): Boolean =
        oldItem == newItem

}