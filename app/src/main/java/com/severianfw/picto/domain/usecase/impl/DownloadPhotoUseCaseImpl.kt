package com.severianfw.picto.domain.usecase.impl

import com.severianfw.picto.domain.usecase.DownloadPhotoUseCase
import com.severianfw.picto.downloadmanager.DownloadManagerHelper
import javax.inject.Inject

class DownloadPhotoUseCaseImpl @Inject constructor(private val downloadManagerHelper: DownloadManagerHelper) :
    DownloadPhotoUseCase {

    override fun invoke(photoUrl: String) {
        downloadManagerHelper.downloadPhoto(photoUrl)
    }
}