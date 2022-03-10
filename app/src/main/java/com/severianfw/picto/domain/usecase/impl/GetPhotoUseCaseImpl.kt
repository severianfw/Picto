package com.severianfw.picto.domain.usecase.impl

import com.severianfw.picto.connectionlistener.InternetConnectionListener
import com.severianfw.picto.data.repository.PhotoRepository
import com.severianfw.picto.domain.model.PhotoItemModel
import com.severianfw.picto.domain.usecase.GetPhotoUseCase
import com.severianfw.picto.utils.PhotoMapper
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetPhotoUseCaseImpl @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val internetConnectionListener: InternetConnectionListener
    ) : GetPhotoUseCase {

    override fun invoke(page: Int): Single<List<PhotoItemModel>> {
        return if (internetConnectionListener.isInternetAvailable()) {
            photoRepository.getPhotos(page).map { response ->
                PhotoMapper.mapToPhotoItemModel(response)
            }
        } else {
            photoRepository.getLocalPhotos()
        }
    }
}