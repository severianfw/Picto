package com.severianfw.picto.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.severianfw.picto.data.remote.ImageUrl
import com.severianfw.picto.data.remote.PhotoResponse
import com.severianfw.picto.domain.GetPhotoUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeViewModel @Inject constructor(
    private val getPhotoUseCase: GetPhotoUseCase
) : ViewModel() {

    private val _photos = MutableLiveData<List<ImageUrl>>()
    val photos: LiveData<List<ImageUrl>> = _photos

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _pageNum: Int = 1

    private val compositeDisposable = CompositeDisposable()
    private val newPhotos = mutableListOf<ImageUrl>()

    init {
        getPhotos()
    }

    private fun getPhotos() {
        _isLoading.value = true
        compositeDisposable.add(
            getPhotoUseCase(_pageNum).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<PhotoResponse>>() {
                    override fun onSuccess(t: List<PhotoResponse>) {
                        for (item in t) {
                            item.urls?.let { newPhotos.add(it) }
                        }
                        _photos.value = newPhotos
                        _isLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        Log.d("RESPONSE", e.message.toString())
                    }

                })
        )
    }

    fun loadMorePage() {
        if (_pageNum <= 11) {
            _pageNum += 1
            getPhotos()
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}