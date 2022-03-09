package com.severianfw.picto.domain.usecase

import com.severianfw.picto.downloadmanager.DownloadManagerHelper
import javax.inject.Inject

class DownloadPhotoUseCaseImpl @Inject constructor(private val downloadManagerHelper: DownloadManagerHelper) :
    DownloadPhotoUseCase {

    override fun invoke(photoUrl: String) {
        downloadManagerHelper.downloadPhoto(photoUrl)
    }
}