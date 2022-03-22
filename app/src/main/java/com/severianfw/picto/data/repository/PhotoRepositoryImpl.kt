package com.severianfw.picto.data.repository

import com.severianfw.picto.connectionlistener.InternetConnectionListener
import com.severianfw.picto.data.local.PhotoDao
import com.severianfw.picto.data.remote.ApiService
import com.severianfw.picto.data.remote.SearchPhotoResponse
import com.severianfw.picto.utils.PhotoMapper
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val photoDao: PhotoDao,
    private val internetConnectionListener: InternetConnectionListener
) : PhotoRepository {

    companion object {
        const val CLIENT_ID = "eH_2mMyrCefjXtAmoudvsdfA6qdHD4ju6jF3yFkY5UU"
        const val PAGE_SIZE = 10
    }

    override fun getPhotos(page: Int, isInitial: Boolean): Single<PhotoState> {
        return if (internetConnectionListener.isInternetAvailable()) {
            Single.fromCallable { initialDeleteLocalPhotos(isInitial) }
                .flatMap { getPhotosFromApi(page) }
        } else {
            getLocalPhotos(page)
        }
    }

    private fun initialDeleteLocalPhotos(isInitial: Boolean) {
        if (isInitial) {
            deleteLocalPhotos()
        }
    }

    override fun getPhotosFromApi(page: Int): Single<PhotoState> {
        return apiService.getPhotos(CLIENT_ID, PAGE_SIZE, page)
            .doOnSuccess { photo ->
                photoDao.insertPhotos(PhotoMapper.mapResponseToPhotoEntity(photo))
            }
            .map { response ->
                PhotoState.PhotoRemoteModel(response)
            }
    }

    override fun getLocalPhotos(page: Int): Single<PhotoState> {
        // Ignore Load More
        if (page == 1) {
            return photoDao.getPhotos().map { photos -> PhotoState.PhotoLocalModel(photos) }
        }
        return Single.just(PhotoState.PhotoLocalModel(emptyList()))
    }

    override fun searchPhotos(page: Int, photoName: String): Single<SearchPhotoResponse> {
        return apiService.searchPhotos(CLIENT_ID, PAGE_SIZE, page, photoName)
    }

    override fun deleteLocalPhotos() {
        return photoDao.deletePhotos()
    }

}

