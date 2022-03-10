package com.severianfw.picto.data.remote

import com.google.gson.annotations.SerializedName

data class SearchPhotoResponse(

    @field:SerializedName("results")
    val results: List<PhotoResponse>? = null
)