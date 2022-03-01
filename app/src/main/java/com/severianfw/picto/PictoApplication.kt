package com.severianfw.picto

import android.app.Application
import com.severianfw.picto.di.AppComponent
import com.severianfw.picto.di.DaggerAppComponent

class PictoApplication: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create()
    }
}