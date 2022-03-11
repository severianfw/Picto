package com.severianfw.picto.data.repository

import com.severianfw.picto.data.remote.PhotoResponse
import com.severianfw.picto.data.remote.SearchPhotoResponse
import io.reactivex.rxjava3.core.Single

interface PhotoRepository {

    fun getPhotos(page: Int): Single<List<PhotoResponse>>
    fun searchPhotos(page: Int, photoName: String): Single<SearchPhotoResponse>
}