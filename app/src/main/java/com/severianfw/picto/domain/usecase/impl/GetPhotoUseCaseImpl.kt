package com.severianfw.picto.domain.usecase.impl

import com.severianfw.picto.data.repository.PhotoRepository
import com.severianfw.picto.data.repository.PhotoState
import com.severianfw.picto.domain.model.PhotoItemModel
import com.severianfw.picto.domain.usecase.GetPhotoUseCase
import com.severianfw.picto.utils.PhotoMapper
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetPhotoUseCaseImpl @Inject constructor(
    private val photoRepository: PhotoRepository
) : GetPhotoUseCase {

    override fun invoke(page: Int, isInitial: Boolean): Single<List<PhotoItemModel>> {
        return getPhotoItemModel(page, isInitial)
    }

    private fun getPhotoItemModel(page: Int, isInitial: Boolean): Single<List<PhotoItemModel>> {
        return photoRepository.getPhotos(page, isInitial)
            .map { photoState ->
                mapPhotoState(photoState)
            }
    }

    private fun mapPhotoState(photoState: PhotoState): List<PhotoItemModel> {
        return when (photoState) {
            is PhotoState.PhotoRemoteModel -> {
                PhotoMapper.mapResponseToPhotoItemModel(photoState.photos)
            }
            is PhotoState.PhotoLocalModel -> {
                PhotoMapper.mapPhotoEntityToPhotoItemModel(photoState.photos)
            }
        }
    }

}