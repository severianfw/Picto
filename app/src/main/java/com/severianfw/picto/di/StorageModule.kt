package com.severianfw.picto.di

import android.content.Context
import androidx.room.Room
import com.severianfw.picto.data.local.PhotoDao
import com.severianfw.picto.data.local.PhotoRoomDatabase
import com.severianfw.picto.data.repository.DarkModeRepository
import com.severianfw.picto.utils.DarkModeUtil
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {

    @Singleton
    @Provides
    fun providesRoomDatabase(context: Context): PhotoRoomDatabase {
        return Room.databaseBuilder(
            context,
            PhotoRoomDatabase::class.java,
            "photo_database"
        ).build()
    }

    @Singleton
    @Provides
    fun providesPhotoDao(photoRoomDatabase: PhotoRoomDatabase): PhotoDao {
        return photoRoomDatabase.getPhotoDao()
    }

    @Singleton
    @Provides
    fun providesDarkModeUtil(darkModeRepository: DarkModeRepository): DarkModeUtil {
        return DarkModeUtil(darkModeRepository)
    }
}