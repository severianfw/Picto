package com.severianfw.picto.utils

import com.severianfw.picto.data.remote.PhotoResponse
import com.severianfw.picto.domain.model.PhotoItemModel

object PhotoMapper {
    fun mapToPhotoItemModel(response: List<PhotoResponse>): List<PhotoItemModel> {
        return response.map { photo ->
            PhotoItemModel(
                id = photo.id.orEmpty(),
                thumbnailImageUri = photo.urls?.regular.orEmpty(),
                mainImageUri = photo.urls?.small.orEmpty(),
                description = photo.description.orEmpty(),
                profilePictureUri = photo.user?.profileImage?.medium.orEmpty(),
                authorName = photo.user?.name.orEmpty()
            )
        }
    }
}