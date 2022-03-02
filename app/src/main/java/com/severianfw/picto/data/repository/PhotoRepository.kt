package com.severianfw.picto.data.repository

import com.severianfw.picto.data.remote.ApiService
import com.severianfw.picto.data.remote.PhotosResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val apiService: ApiService
) {

    companion object {
        const val CLIENT_ID = "eH_2mMyrCefjXtAmoudvsdfA6qdHD4ju6jF3yFkY5UU"
    }

    fun getPhotos(): Single<PhotosResponse> {
        return apiService.getPhotos(CLIENT_ID, 8, 1)
    }

}