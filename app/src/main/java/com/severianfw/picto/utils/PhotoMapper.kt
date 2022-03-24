package com.severianfw.picto.utils

import com.severianfw.picto.data.local.entity.PhotoEntity
import com.severianfw.picto.data.remote.PhotoResponse
import com.severianfw.picto.domain.model.PhotoItemModel

object PhotoMapper {
    fun mapResponseToPhotoItemModel(responses: List<PhotoResponse>): List<PhotoItemModel> {
        return responses.map { photo ->
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
    fun mapResponseToPhotoEntity(responses: List<PhotoResponse>): List<PhotoEntity> {
        return responses.map { photo ->
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
    fun mapPhotoEntityToPhotoItemModel(entities: List<PhotoEntity>): List<PhotoItemModel> {
        return entities.map { photo ->
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