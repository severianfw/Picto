package com.severianfw.picto.data.repository

import com.severianfw.picto.data.local.entity.PhotoEntity
import com.severianfw.picto.data.remote.PhotoResponse

sealed class PhotoState {
    class PhotoRemoteModel(val photos: List<PhotoResponse>) : PhotoState()
    class PhotoLocalModel(val photos: List<PhotoEntity>) : PhotoState()
}