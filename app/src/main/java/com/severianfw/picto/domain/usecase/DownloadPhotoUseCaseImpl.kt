package com.severianfw.picto.domain.usecase

import com.severianfw.picto.downloadmanager.DownloadManagerHelperImpl
import javax.inject.Inject

class DownloadPhotoUseCaseImpl @Inject constructor(private val downloadManagerHelperImpl: DownloadManagerHelperImpl) :
    DownloadPhotoUseCase {

    override fun invoke(photoUrl: String) {
        downloadManagerHelperImpl.downloadPhoto(photoUrl)
    }
}