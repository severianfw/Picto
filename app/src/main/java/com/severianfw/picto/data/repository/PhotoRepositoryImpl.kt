package com.severianfw.picto.data.repository

import com.severianfw.picto.data.local.PhotoDao
import com.severianfw.picto.data.remote.ApiService
import com.severianfw.picto.data.remote.PhotoResponse
import com.severianfw.picto.data.remote.SearchPhotoResponse
import com.severianfw.picto.domain.model.PhotoItemModel
import com.severianfw.picto.utils.PhotoMapper
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val photoDao: PhotoDao,
    private val dispatcherIO: CoroutineDispatcher
) : PhotoRepository {

    companion object {
        const val CLIENT_ID = "eH_2mMyrCefjXtAmoudvsdfA6qdHD4ju6jF3yFkY5UU"
        const val PAGE_SIZE = 10
    }

    override fun getPhotos(page: Int): Single<List<PhotoResponse>> {
        return apiService.getPhotos(CLIENT_ID, PAGE_SIZE, page).doOnSuccess {
            CoroutineScope(dispatcherIO).launch {
                val photos = PhotoMapper.mapToPhotoItemModel(it)
                photoDao.insertPhotos(photos)
            }
        }
    }

    override fun searchPhotos(page: Int, photoName: String): Single<SearchPhotoResponse> {
        return apiService.searchPhotos(CLIENT_ID, PAGE_SIZE, page, photoName)
    }

    override fun getLocalPhotos(): Single<List<PhotoItemModel>> {
        return photoDao.getPhotos()
    }

    override fun deleteLocalPhotos() {
        return photoDao.deletePhotos()
    }

}