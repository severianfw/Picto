package com.severianfw.picto.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.severianfw.picto.data.remote.ImageUrl
import com.severianfw.picto.databinding.ItemPhotosBinding

class PhotoAdapter(private val listPhotos: List<ImageUrl>) :
    ListAdapter<ImageUrl, PhotoAdapter.ViewHolder>(PhotoItemDiffCallback()) {

    inner class ViewHolder(private val binding: ItemPhotosBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: ImageUrl) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPhotosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listPhotos[position])
    }
}

class PhotoItemDiffCallback : DiffUtil.ItemCallback<ImageUrl>() {
    override fun areItemsTheSame(oldItem: ImageUrl, newItem: ImageUrl): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: ImageUrl, newItem: ImageUrl): Boolean =
        oldItem == newItem

}