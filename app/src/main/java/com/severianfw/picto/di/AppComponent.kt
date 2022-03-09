package com.severianfw.picto.di

import android.app.Application
import com.severianfw.picto.view.MainActivity
import com.severianfw.picto.view.home.HomeFragment
import com.severianfw.picto.view.home.SettingsBottomSheetDialogFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Application): AppComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(homeFragment: HomeFragment)

}