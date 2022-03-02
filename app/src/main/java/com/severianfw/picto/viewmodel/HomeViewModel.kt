package com.severianfw.picto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.severianfw.picto.data.remote.ImageUrl
import com.severianfw.picto.data.remote.PhotosResponse
import com.severianfw.picto.domain.GetPhotoUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getPhotoUseCase: GetPhotoUseCase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private fun getPhotos() {
        compositeDisposable.add(
            getPhotoUseCase.getPhotos().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<PhotosResponse>() {
                    override fun onSuccess(t: PhotosResponse) {
                        TODO("Not yet implemented")
                    }

                    override fun onError(e: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
        )

    }


}