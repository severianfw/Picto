package com.severianfw.picto.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.severianfw.picto.data.local.entity.PhotoEntity
import io.reactivex.rxjava3.core.Single

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPhotos(photo: List<PhotoEntity>)

    @Query("SELECT * from photo")
    fun getPhotos(): Single<List<PhotoEntity>>

    @Query("DELETE from photo")
    fun deletePhotos()
}