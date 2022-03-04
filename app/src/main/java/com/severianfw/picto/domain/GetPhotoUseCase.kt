package com.severianfw.picto.domain

import com.severianfw.picto.data.remote.PhotoResponse
import io.reactivex.rxjava3.core.Single

interface GetPhotoUseCase {

    operator fun invoke(page: Int): Single<List<PhotoResponse>>

}