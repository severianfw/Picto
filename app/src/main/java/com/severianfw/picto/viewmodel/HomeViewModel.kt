package com.severianfw.picto.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.severianfw.picto.data.local.PhotoRoomDatabase
import com.severianfw.picto.domain.model.PhotoItemModel
import com.severianfw.picto.domain.usecase.GetPhotoUseCase
import com.severianfw.picto.domain.usecase.SearchPhotoUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeViewModel @Inject constructor(
    private val getPhotoUseCase: GetPhotoUseCase,
    private val searchPhotoUseCase: SearchPhotoUseCase,
    private val photoRoomDatabase: PhotoRoomDatabase
) : ViewModel() {

    private val _photos = MutableLiveData<List<PhotoItemModel>>()
    val photos: LiveData<List<PhotoItemModel>>
        get() = _photos

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _hasError = MutableLiveData<Boolean>()
    val hasError: LiveData<Boolean>
        get() = _hasError

    var isInitial: Boolean = true

    private var pageNumber: Int = 1
    private var isSearching = false
    private var photoName: String = ""

    private val compositeDisposable = CompositeDisposable()

    fun getPhotos() {
        _isLoading.postValue(true)
        compositeDisposable.add(
            getPhotoUseCase(pageNumber).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    _isLoading.postValue(false)
                }
                .subscribeWith(object : DisposableSingleObserver<List<PhotoItemModel>>() {
                    override fun onSuccess(newPhotos: List<PhotoItemModel>) {
                        _photos.value = _photos.value.orEmpty().plus(newPhotos)
                    }

                    override fun onError(e: Throwable) {
                        Log.d("MESSAGE", e.message.toString())
                        _hasError.value = true
                    }

                })
        )
    }


    fun loadMorePage() {
        pageNumber += 1
        if (isSearching) {
            searchPhotos(photoName)
        } else {
            getPhotos()
        }
    }

    fun searchPhotos(photoName: String) {
        this.photoName = photoName
        isSearching = true
        _isLoading.value = true
        compositeDisposable.add(
            searchPhotoUseCase(pageNumber, photoName).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { _isLoading.value = false }
                .subscribeWith(object : DisposableSingleObserver<List<PhotoItemModel>>() {
                    override fun onSuccess(newPhotos: List<PhotoItemModel>) {
                        _photos.value = _photos.value.orEmpty().plus(newPhotos)
                    }

                    override fun onError(e: Throwable) {
                        Log.d("MESSAGE", e.message.toString())
                        _hasError.value = true
                    }

                })
        )
    }

    fun clearPhotoList() {
        viewModelScope.launch(Dispatchers.IO) {
            val emptyPhotoList = mutableListOf<PhotoItemModel>()
            _photos.postValue(emptyPhotoList)
        }
    }

    fun clearPhotoDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            photoRoomDatabase.clearAllTables()
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}