package com.severianfw.picto.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoItemModel(
    val id: String = "",
    val thumbnailImageUri: String = "",
    val mainImageUri: String = "",
    val description: String = "",
    val profilePictureUri: String = "",
    val authorName: String = "",
) : Parcelable