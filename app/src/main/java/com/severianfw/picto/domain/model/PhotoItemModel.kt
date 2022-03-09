package com.severianfw.picto.domain.model

data class PhotoItemModel(
    val id: String = "",
    val thumbnailImageUri: String = "",
    val mainImageUri: String = "",
    val description: String = "",
    val profilePictureUri: String = "",
    val authorName: String = "",
)