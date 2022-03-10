package com.severianfw.picto.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.severianfw.picto.domain.model.PhotoItemModel

@Database(entities = [PhotoItemModel::class], version = PhotoRoomDatabase.VERSION)
abstract class PhotoRoomDatabase : RoomDatabase() {

    companion object {
        const val VERSION = 1
    }

    abstract fun getPhotoDao(): PhotoDao

}