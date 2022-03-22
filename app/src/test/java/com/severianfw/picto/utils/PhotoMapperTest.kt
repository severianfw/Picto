package com.severianfw.picto.utils

import com.severianfw.picto.data.local.PhotoEntity
import com.severianfw.picto.data.remote.ImageUrl
import com.severianfw.picto.data.remote.PhotoResponse
import com.severianfw.picto.data.remote.ProfileImage
import com.severianfw.picto.data.remote.User
import com.severianfw.picto.domain.model.PhotoItemModel
import org.junit.Assert
import org.junit.Test

class PhotoMapperTest {

    @Test
    fun `when mapResponseToPhotoItemModel and PhotoResponse fields are null then result must be valid`() {
        // Given
        val dummyPhotoResponse = PhotoResponse()
        val dummyPhotoItemModel = PhotoItemModel()

        // When
        val result = PhotoMapper.mapResponseToPhotoItemModel(listOf(dummyPhotoResponse))

        // Then
        Assert.assertEquals(result, listOf(dummyPhotoItemModel))
    }

    @Test
    fun `when mapResponseToPhotoItemModel and PhotoResponse fields have value then result must be valid`() {
        // Given
        val dummyPhotoResponse =
            PhotoResponse(
                "desc",
                ImageUrl(
                    regular = "url_reg",
                    small = "url_small"
                ),
                "id_photo",
                User(
                    name = "user_name",
                    profileImage = ProfileImage(medium = "url_profile")
                )
            )
        val dummyPhotoItemModel =
            PhotoItemModel(
                id = "id_photo",
                thumbnailImageUri = "url_reg",
                mainImageUri = "url_small",
                description = "desc",
                profilePictureUri = "url_profile",
                authorName = "user_name"
            )

        // When
        val result = PhotoMapper.mapResponseToPhotoItemModel(listOf(dummyPhotoResponse))

        // Then
        Assert.assertEquals(result, listOf(dummyPhotoItemModel))
    }

    @Test
    fun `when mapResponseToPhotoEntity and PhotoEntity fields are null then result must be valid`() {
        // Given
        val dummyPhotoResponse = PhotoResponse()
        val dummyPhotoEntity = PhotoEntity()

        // When
        val result = PhotoMapper.mapResponseToPhotoEntity(listOf(dummyPhotoResponse))

        // Then
        Assert.assertEquals(result, listOf(dummyPhotoEntity))
    }

    @Test
    fun `when mapResponseToPhotoEntity and PhotoEntity fields have value then result must be valid`() {
        // Given
        val dummyPhotoResponse =
            PhotoResponse(
                "desc",
                ImageUrl(
                    regular = "url_reg",
                    small = "url_small"
                ),
                "id_photo",
                User(
                    name = "user_name",
                    profileImage = ProfileImage(medium = "url_profile")
                )
            )
        val dummyPhotoEntity =
            PhotoEntity(
                id = "id_photo",
                thumbnailImageUri = "url_reg",
                mainImageUri = "url_small",
                description = "desc",
                profilePictureUri = "url_profile",
                authorName = "user_name"
            )

        // When
        val result = PhotoMapper.mapResponseToPhotoEntity(listOf(dummyPhotoResponse))

        // Then
        Assert.assertEquals(result, listOf(dummyPhotoEntity))
    }

    @Test
    fun `when mapPhotoEntityToPhotoItemModel and PhotoEntity fields are null then result must be valid`() {
        // Given
        val dummyPhotoEntity = PhotoEntity()
        val dummyPhotoItemModel = PhotoItemModel()

        // When
        val result = PhotoMapper.mapPhotoEntityToPhotoItemModel(listOf(dummyPhotoEntity))

        // Then
        Assert.assertEquals(result, listOf(dummyPhotoItemModel))
    }

    @Test
    fun `when mapPhotoEntityToPhotoItemModel and PhotoEntity fields have value then result must be valid`() {
        // Given
        val dummyPhotoEntity = PhotoEntity(
            id = "id_photo",
            thumbnailImageUri = "url_reg",
            mainImageUri = "url_small",
            description = "desc",
            profilePictureUri = "url_profile",
            authorName = "user_name"
        )
        val dummyPhotoItemModel = PhotoItemModel(
            id = "id_photo",
            thumbnailImageUri = "url_reg",
            mainImageUri = "url_small",
            description = "desc",
            profilePictureUri = "url_profile",
            authorName = "user_name"
        )

        // When
        val result = PhotoMapper.mapPhotoEntityToPhotoItemModel(listOf(dummyPhotoEntity))

        // Then
        Assert.assertEquals(result, listOf(dummyPhotoItemModel))
    }
}