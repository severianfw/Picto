package com.severianfw.picto.di

import com.severianfw.picto.data.repository.PhotoRepository
import com.severianfw.picto.data.repository.PhotoRepositoryImpl
import com.severianfw.picto.domain.GetPhotoUseCase
import com.severianfw.picto.domain.GetPhotoUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun provideGetPhotoRepository(photoRepositoryImpl: PhotoRepositoryImpl): PhotoRepository

    @Binds
    abstract fun provideGetPhotoUseCase(getPhotoUseCaseImpl: GetPhotoUseCaseImpl): GetPhotoUseCase
}