package com.severianfw.picto.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "photo")
@Parcelize
data class PhotoItemModel(
    @PrimaryKey
    val id: String = "",
    val thumbnailImageUri: String = "",
    val mainImageUri: String = "",
    val description: String = "",
    val profilePictureUri: String = "",
    val authorName: String = "",
) : Parcelable