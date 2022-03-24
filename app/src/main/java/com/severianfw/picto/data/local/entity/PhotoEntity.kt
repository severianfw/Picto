package com.severianfw.picto.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo")
data class PhotoEntity(
    @PrimaryKey
    val id: String = "",
    val thumbnailImageUri: String = "",
    val mainImageUri: String = "",
    val description: String = "",
    val profilePictureUri: String = "",
    val authorName: String = ""
)