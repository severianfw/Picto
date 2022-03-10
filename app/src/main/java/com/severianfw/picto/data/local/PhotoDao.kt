package com.severianfw.picto.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.severianfw.picto.domain.model.PhotoItemModel
import io.reactivex.rxjava3.core.Single

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(photo: PhotoItemModel)

    @Query("SELECT * from photo")
    fun getAllPhoto(): Single<List<PhotoItemModel>>
}