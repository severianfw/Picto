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

    private val _listPhotos = MutableLiveData<List<ImageUrl>>()
    val listPhotos: LiveData<List<ImageUrl>> = _listPhotos

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val compositeDisposable = CompositeDisposable()

    fun getPhotos() {
        val photos = mutableListOf<ImageUrl>()
        _isLoading.value = true
        compositeDisposable.add(
            getPhotoUseCase.getPhotos().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<PhotoResponse>>() {
                    override fun onSuccess(t: List<PhotoResponse>) {
                        for (item in t) {
                            item.urls?.let { photos.add(it) }
                        }
                        _listPhotos.value = photos
                        _isLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        Log.d("RESPONSE", e.message.toString())
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}