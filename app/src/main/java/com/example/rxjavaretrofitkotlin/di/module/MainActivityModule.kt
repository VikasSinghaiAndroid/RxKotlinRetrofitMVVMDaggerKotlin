package com.example.rxjavaretrofitkotlin.di.module

import com.example.rxjavaretrofitkotlin.repository.Repository
import com.example.rxjavaretrofitkotlin.utils.CommonUtils
import com.example.rxjavaretrofitkotlin.utils.SchedulerProvider
import com.example.rxjavaretrofitkotlin.viewmodel.MainActivityViewModel
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun provideViewModel(
        repository: Repository,
        schedulerProvider: SchedulerProvider,
        commonUtils: CommonUtils
    ) =
        MainActivityViewModel(repository, schedulerProvider, commonUtils)
}