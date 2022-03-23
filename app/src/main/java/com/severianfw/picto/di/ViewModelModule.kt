package com.severianfw.picto.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.severianfw.picto.viewmodel.HomeViewModel
import com.severianfw.picto.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun bindsHomeViewModel(viewModel: HomeViewModel): ViewModel

}