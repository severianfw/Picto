package com.severianfw.picto.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("photos")
    fun getPhotos(
        @Query("client_id") clientId: String,
        @Query("per_page") itemCount: Int
    ): Call<PhotosResponse>

}