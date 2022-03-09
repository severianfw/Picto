package com.severianfw.picto.domain.usecase

interface DownloadPhotoUseCase {

    operator fun invoke(photoUrl: String)
}