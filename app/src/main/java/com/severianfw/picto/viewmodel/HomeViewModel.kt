package com.severianfw.picto.viewmodel

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
    val photos: LiveData<List<ImageUrl>> get() = _photos

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isResponseSuccess = MutableLiveData<Boolean>()
    val isResponseSuccess: LiveData<Boolean> get() = _isResponseSuccess

    var isInitial: Boolean = false

    private var pageNumber: Int = 1

    private val compositeDisposable = CompositeDisposable()
    private val newPhoto = mutableListOf<ImageUrl>()

    fun getPhotos() {
        _isLoading.value = true
        compositeDisposable.add(
            getPhotoUseCase(pageNumber).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { _isLoading.value = false }
                .subscribeWith(object : DisposableSingleObserver<List<PhotoResponse>>() {
                    override fun onSuccess(response: List<PhotoResponse>) {
                        for (item in response) {
                            item.urls?.let { newPhoto.add(it) }
                        }
                        _photos.value = newPhoto
                        _isResponseSuccess.value = true
                    }

                    override fun onError(e: Throwable) {
                        _isResponseSuccess.value = false
                    }

                })
        )
    }

    fun loadMorePage() {
        if (pageNumber <= 11) {
            pageNumber += 1
            getPhotos()
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}