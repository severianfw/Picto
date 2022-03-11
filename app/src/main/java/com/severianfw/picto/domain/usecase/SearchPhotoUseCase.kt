package com.severianfw.picto.domain.usecase

import com.severianfw.picto.domain.model.PhotoItemModel
import io.reactivex.rxjava3.core.Single

interface SearchPhotoUseCase {

    operator fun invoke(page: Int, photoName: String) : Single<List<PhotoItemModel>>
}