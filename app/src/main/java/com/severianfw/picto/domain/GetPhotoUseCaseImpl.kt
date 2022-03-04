package com.severianfw.picto.domain

import com.severianfw.picto.data.remote.PhotoResponse
import com.severianfw.picto.data.repository.PhotoRepositoryImpl
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetPhotoUseCaseImpl @Inject constructor(private val photosRepositoryImpl: PhotoRepositoryImpl) :
    GetPhotoUseCase {

    override fun getPhotos(): Single<List<PhotoResponse>> {
        return photosRepositoryImpl.getPhotos()
    }
}