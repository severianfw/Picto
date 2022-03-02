package com.severianfw.picto.di

import com.severianfw.picto.data.remote.NetworkModule
import com.severianfw.picto.view.MainActivity
import dagger.Component

@Component(modules = [NetworkModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(): AppComponent
    }

    fun inject(mainActivity: MainActivity)

}