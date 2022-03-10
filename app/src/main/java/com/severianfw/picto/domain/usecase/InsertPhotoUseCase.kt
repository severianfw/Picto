package com.severianfw.picto.domain.usecase

import com.severianfw.picto.domain.model.PhotoItemModel

interface InsertPhotoUseCase {

    operator fun invoke(photos: List<PhotoItemModel>)
}