package com.severianfw.picto.domain.usecase

import com.severianfw.picto.domain.model.PhotoItemModel
import io.reactivex.rxjava3.core.Single

interface GetOfflinePhotoUseCase {

    operator fun invoke(): Single<List<PhotoItemModel>>
}