package com.severianfw.picto.domain.usecase

import com.severianfw.picto.data.repository.PhotoRepository
import com.severianfw.picto.domain.model.PhotoItemModel
import com.severianfw.picto.utils.PhotoMapper
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SearchPhotoUseCaseImpl @Inject constructor(
    private val photoRepository: PhotoRepository
) : SearchPhotoUseCase {

    override fun invoke(page: Int, photoName: String): Single<List<PhotoItemModel>> {
        return photoRepository.searchPhotos(page, photoName).map { response ->
            PhotoMapper.mapToPhotoItemModel(response.results.orEmpty())
        }
    }
}