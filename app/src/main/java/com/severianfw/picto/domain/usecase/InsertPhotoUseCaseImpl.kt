package com.severianfw.picto.domain.usecase

import com.severianfw.picto.data.repository.PhotoRepository
import com.severianfw.picto.domain.model.PhotoItemModel
import javax.inject.Inject

class InsertPhotoUseCaseImpl @Inject constructor(
    private val photoRepository: PhotoRepository
) : InsertPhotoUseCase {

    override fun invoke(photos: List<PhotoItemModel>) {
        for (item in photos) {
            photoRepository.insertPhotos(item)
        }
    }
}