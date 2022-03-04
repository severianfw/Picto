package com.severianfw.picto.di

import android.app.Application
import com.severianfw.picto.data.remote.NetworkModule
import com.severianfw.picto.view.MainActivity
import com.severianfw.picto.view.home.SettingsBottomSheetDialogFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [NetworkModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Application): AppComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(settingsBottomSheetDialogFragment: SettingsBottomSheetDialogFragment)

}