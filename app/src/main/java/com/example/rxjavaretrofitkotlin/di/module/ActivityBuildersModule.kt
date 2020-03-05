package com.example.rxjavaretrofitkotlin.di.module

import com.example.rxjavaretrofitkotlin.ui.DetailActivity
import com.example.rxjavaretrofitkotlin.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


// declare all the activity here , dependency of activity are provided by this module

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector()
    abstract fun contributeDetailedActivity(): DetailActivity

}