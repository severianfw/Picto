package com.severianfw.picto.data.remote

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/photos")
    fun getPhotos(
        @Query("client_id") clientId: String,
        @Query("per_page") itemCount: Int
    ): Single<PhotosResponse>

}