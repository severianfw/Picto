package com.severianfw.picto.di

import com.severianfw.picto.connectionlistener.InternetConnectionListener
import com.severianfw.picto.connectionlistener.InternetConnectionListenerImpl
import com.severianfw.picto.data.repository.DarkModeRepository
import com.severianfw.picto.data.repository.DarkModeRepositoryImpl
import com.severianfw.picto.data.repository.PhotoRepository
import com.severianfw.picto.data.repository.PhotoRepositoryImpl
import com.severianfw.picto.domain.usecase.*
import com.severianfw.picto.domain.usecase.impl.DownloadPhotoUseCaseImpl
import com.severianfw.picto.domain.usecase.impl.GetPhotoUseCaseImpl
import com.severianfw.picto.domain.usecase.impl.SearchPhotoUseCaseImpl
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
    abstract fun bindsSearchPhotoUseCase(searchPhotoUseCaseImpl: SearchPhotoUseCaseImpl): SearchPhotoUseCase

    @Singleton
    @Binds
    abstract fun bindsDownloadManagerHelper(downloadManagerHelperImpl: DownloadManagerHelperImpl): DownloadManagerHelper

    @Singleton
    @Binds
    abstract fun bindsDownloadManagerUseCase(downloadPhotoUseCaseImpl: DownloadPhotoUseCaseImpl): DownloadPhotoUseCase

    @Singleton
    @Binds
    abstract fun bindsInternetConnectionListener(internetConnectionListenerImpl: InternetConnectionListenerImpl): InternetConnectionListener

    @Singleton
    @Binds
    abstract fun bindsDarkModeRepository(darkModeRepositoryImpl: DarkModeRepositoryImpl): DarkModeRepository

}