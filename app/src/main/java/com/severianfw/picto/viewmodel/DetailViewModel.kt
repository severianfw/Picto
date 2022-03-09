package com.severianfw.picto.viewmodel

import androidx.lifecycle.ViewModel
import com.severianfw.picto.domain.usecase.DownloadPhotoUseCase
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val downloadPhotoUseCase: DownloadPhotoUseCase
) : ViewModel() {

    fun downloadPhoto(photoUrl: String) {
        downloadPhotoUseCase(photoUrl)
    }

}