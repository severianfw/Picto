package com.severianfw.picto.data.repository

import com.severianfw.picto.data.remote.ApiService
import com.severianfw.picto.data.remote.PhotoResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): PhotoRepository {

    companion object {
        const val CLIENT_ID = "eH_2mMyrCefjXtAmoudvsdfA6qdHD4ju6jF3yFkY5UU"
    }

    override fun getPhotos(page: Int): Single<List<PhotoResponse>> {
        return apiService.getPhotos(CLIENT_ID, 8, page)
    }

}