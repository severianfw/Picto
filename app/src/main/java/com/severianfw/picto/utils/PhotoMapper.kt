package com.severianfw.picto.utils

import com.severianfw.picto.data.local.PhotoEntity
import com.severianfw.picto.data.remote.PhotoResponse
import com.severianfw.picto.domain.model.PhotoItemModel

object PhotoMapper {
    fun mapResponseToPhotoItemModel(response: List<PhotoResponse>): List<PhotoItemModel> {
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
    fun mapResponseToPhotoEntity(response: List<PhotoResponse>): List<PhotoEntity> {
        return response.map { photo ->
            PhotoEntity(
                id = photo.id.orEmpty(),
                thumbnailImageUri = photo.urls?.regular.orEmpty(),
                mainImageUri = photo.urls?.small.orEmpty(),
                description = photo.description.orEmpty(),
                profilePictureUri = photo.user?.profileImage?.medium.orEmpty(),
                authorName = photo.user?.name.orEmpty()
            )
        }
    }
    fun mapPhotoEntityToPhotoItemModel(entity: List<PhotoEntity>): List<PhotoItemModel> {
        return entity.map { photo ->
            PhotoItemModel(
                id = photo.id,
                thumbnailImageUri = photo.thumbnailImageUri,
                mainImageUri = photo.mainImageUri,
                description = photo.description,
                profilePictureUri = photo.profilePictureUri,
                authorName = photo.authorName
            )
        }
    }
}