package com.severianfw.picto.domain

import com.severianfw.picto.data.remote.PhotoResponse
import com.severianfw.picto.data.repository.PhotoRepository
import com.severianfw.picto.data.repository.PhotoRepositoryImpl
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetPhotoUseCaseImpl @Inject constructor(private val photoRepository: PhotoRepository) :
    GetPhotoUseCase {

    override fun invoke(page: Int): Single<List<PhotoResponse>> {
        return photoRepository.getPhotos(page)
    }
}