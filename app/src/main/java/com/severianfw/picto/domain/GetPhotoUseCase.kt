package com.severianfw.picto.domain

import com.severianfw.picto.data.remote.PhotoResponse
import io.reactivex.rxjava3.core.Single

interface GetPhotoUseCase {

    fun getPhotos(): Single<List<PhotoResponse>>
}