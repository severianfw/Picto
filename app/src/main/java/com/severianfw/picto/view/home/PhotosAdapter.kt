package com.severianfw.picto.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.severianfw.picto.data.remote.ImageUrls
import com.severianfw.picto.databinding.ItemPhotosBinding

class PhotosAdapter(private val listPhotos: List<ImageUrls>): RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemPhotosBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: ImageUrls) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPhotosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listPhotos[position])
    }

    override fun getItemCount(): Int = listPhotos.size
}