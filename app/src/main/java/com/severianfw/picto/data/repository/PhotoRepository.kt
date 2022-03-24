package com.severianfw.picto.data.repository

import com.severianfw.picto.data.remote.SearchPhotoResponse
import io.reactivex.rxjava3.core.Single

interface PhotoRepository {

    fun getPhotos(page: Int, isInitial: Boolean): Single<PhotoState>
    fun searchPhotos(page: Int, photoName: String): Single<SearchPhotoResponse>
    fun getPhotosFromRemote(page: Int): Single<PhotoState.PhotoRemoteModel>
    fun getLocalPhotos(page: Int): Single<PhotoState>
    fun deleteLocalPhotos()
}