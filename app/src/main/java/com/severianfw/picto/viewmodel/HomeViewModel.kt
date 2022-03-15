package com.severianfw.picto.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.severianfw.picto.domain.model.PhotoItemModel
import com.severianfw.picto.domain.usecase.GetPhotoUseCase
import com.severianfw.picto.domain.usecase.SearchPhotoUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeViewModel @Inject constructor(
    private val getPhotoUseCase: GetPhotoUseCase,
    private val searchPhotoUseCase: SearchPhotoUseCase
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
    var pageNumber: Int = 1

    var isSearching = false
    var photoName: String = ""

    private val compositeDisposable = CompositeDisposable()

    fun getPhotos() {
        compositeDisposable.add(
            getPhotoUseCase(pageNumber, isInitial).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _isLoading.value = true }
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
        compositeDisposable.add(
            searchPhotoUseCase(pageNumber, photoName).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _isLoading.value = true }
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

    fun clearPhotos() {
        _photos.value = emptyList()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}