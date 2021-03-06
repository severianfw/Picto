package com.severianfw.picto.di

import android.content.Context
import com.severianfw.picto.view.MainActivity
import com.severianfw.picto.view.detail.PhotoDetailActivity
import com.severianfw.picto.view.home.HomeFragment
import com.severianfw.picto.view.home.SettingsBottomSheetDialogFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, StorageModule::class, ViewModelModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(photoDetailActivity: PhotoDetailActivity)
    fun inject(homeFragment: HomeFragment)
    fun inject(settingsBottomSheetDialogFragment: SettingsBottomSheetDialogFragment)

}