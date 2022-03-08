package com.severianfw.picto.di

import com.severianfw.picto.data.repository.PhotoRepository
import com.severianfw.picto.data.repository.PhotoRepositoryImpl
import com.severianfw.picto.domain.GetPhotoUseCase
import com.severianfw.picto.domain.GetPhotoUseCaseImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun bindsGetPhotoRepository(photoRepositoryImpl: PhotoRepositoryImpl): PhotoRepository

    @Singleton
    @Binds
    abstract fun bindsGetPhotoUseCase(getPhotoUseCaseImpl: GetPhotoUseCaseImpl): GetPhotoUseCase
}