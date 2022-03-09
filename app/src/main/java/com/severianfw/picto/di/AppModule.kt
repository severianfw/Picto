package com.severianfw.picto.di

import com.severianfw.picto.data.repository.PhotoRepository
import com.severianfw.picto.data.repository.PhotoRepositoryImpl
import com.severianfw.picto.domain.usecase.DownloadPhotoUseCase
import com.severianfw.picto.domain.usecase.DownloadPhotoUseCaseImpl
import com.severianfw.picto.domain.usecase.GetPhotoUseCase
import com.severianfw.picto.domain.usecase.GetPhotoUseCaseImpl
import com.severianfw.picto.downloadmanager.DownloadManagerHelper
import com.severianfw.picto.downloadmanager.DownloadManagerHelperImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun bindsPhotoRepository(photoRepositoryImpl: PhotoRepositoryImpl): PhotoRepository

    @Singleton
    @Binds
    abstract fun bindsGetPhotoUseCase(getPhotoUseCaseImpl: GetPhotoUseCaseImpl): GetPhotoUseCase

    @Singleton
    @Binds
    abstract fun bindsDownloadManagerHelper(downloadManagerHelperImpl: DownloadManagerHelperImpl): DownloadManagerHelper

    @Singleton
    @Binds
    abstract fun bindsDownloadManagerUseCase(downloadPhotoUseCaseImpl: DownloadPhotoUseCaseImpl): DownloadPhotoUseCase

}