package com.example.rxjavaretrofitkotlin.di.module

import androidx.lifecycle.ViewModel
import com.example.rxjavaretrofitkotlin.di.annotation.ViewModelKey
import com.example.rxjavaretrofitkotlin.viewmodel.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainViewModel(notesViewModel: MainActivityViewModel): ViewModel
}
