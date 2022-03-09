package com.severianfw.picto.domain.usecase

import com.severianfw.picto.domain.model.PhotoItemModel
import io.reactivex.rxjava3.core.Single

interface GetPhotoUseCase {

    operator fun invoke(page: Int): Single<List<PhotoItemModel>>

}