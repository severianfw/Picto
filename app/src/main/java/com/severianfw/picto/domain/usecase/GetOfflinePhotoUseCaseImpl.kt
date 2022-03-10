package com.severianfw.picto.domain.usecase

import com.severianfw.picto.data.repository.PhotoRepository
import com.severianfw.picto.domain.model.PhotoItemModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetOfflinePhotoUseCaseImpl @Inject constructor(
    private val photoRepository: PhotoRepository
) : GetOfflinePhotoUseCase {

    override fun invoke(): Single<List<PhotoItemModel>> {
        return photoRepository.getOfflinePhotos()
    }
}