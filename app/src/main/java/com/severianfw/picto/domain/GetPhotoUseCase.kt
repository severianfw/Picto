package com.severianfw.picto.domain

import com.severianfw.picto.data.remote.ImageUrl
import com.severianfw.picto.data.remote.PhotosResponse
import io.reactivex.rxjava3.core.Single

interface GetPhotoUseCase {

    fun getPhotos(): Single<PhotosResponse>
}