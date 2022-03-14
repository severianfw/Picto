package com.severianfw.picto.domain.usecase.impl

import com.severianfw.picto.connectionlistener.InternetConnectionListener
import com.severianfw.picto.data.repository.PhotoRepository
import com.severianfw.picto.domain.model.PhotoItemModel
import com.severianfw.picto.domain.usecase.GetPhotoUseCase
import com.severianfw.picto.utils.PhotoMapper
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetPhotoUseCaseImpl @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val internetConnectionListener: InternetConnectionListener,
    private val dispatcherIO: CoroutineDispatcher
) : GetPhotoUseCase {

    override fun invoke(page: Int, isInitial: Boolean): Single<List<PhotoItemModel>> {
        return getRemotePhotos(page, isInitial)
    }

    private fun getRemotePhotos(page: Int, isInitial: Boolean): Single<List<PhotoItemModel>> {
        if (internetConnectionListener.isInternetAvailable()) {
            initialDeleteLocalPhotos(isInitial)
            return photoRepository.getPhotos(page).map { response ->
                PhotoMapper.mapToPhotoItemModel(response)
            }
        } else {
            // ignore load more
            if (page == 1) {
                return photoRepository.getLocalPhotos()
            }
            return Single.just(emptyList())
        }
    }

    private fun initialDeleteLocalPhotos(isInitial: Boolean) {
        if (isInitial) {
            CoroutineScope(dispatcherIO).launch {
                photoRepository.deleteLocalPhotos()
            }
        }
    }
}