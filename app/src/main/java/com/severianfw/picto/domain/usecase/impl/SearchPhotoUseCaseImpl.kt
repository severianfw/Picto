package com.severianfw.picto.domain.usecase.impl

import com.severianfw.picto.data.repository.PhotoRepository
import com.severianfw.picto.domain.model.PhotoItemModel
import com.severianfw.picto.domain.usecase.SearchPhotoUseCase
import com.severianfw.picto.utils.PhotoMapper
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SearchPhotoUseCaseImpl @Inject constructor(
    private val photoRepository: PhotoRepository
) : SearchPhotoUseCase {

    override fun invoke(page: Int, photoName: String): Single<List<PhotoItemModel>> {
        return photoRepository.searchPhotos(page, photoName).map { response ->
            PhotoMapper.mapResponseToPhotoItemModel(response.results.orEmpty())
        }
    }
}