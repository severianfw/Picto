package com.severianfw.picto.domain

import com.severianfw.picto.data.remote.PhotosResponse
import com.severianfw.picto.data.repository.PhotoRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetPhotoUseCaseImpl @Inject constructor(private val photosRepository: PhotoRepository) :
    GetPhotoUseCase {

    override fun getPhotos(): Single<PhotosResponse> {
        return photosRepository.getPhotos()
    }
}