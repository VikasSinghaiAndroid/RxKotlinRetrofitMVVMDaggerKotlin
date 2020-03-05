package com.example.rxjavaretrofitkotlin.viewmodel

import com.example.rxjavaretrofitkotlin.model.DataProperty
import com.example.rxjavaretrofitkotlin.repository.Repository
import com.example.rxjavaretrofitkotlin.utils.CommonUtils
import com.example.rxjavaretrofitkotlin.utils.SchedulerProvider
import io.reactivex.Observable

class MainActivityViewModel(
    private val repository: Repository,
    private val schedulerProvider: SchedulerProvider, private val commonUtils: CommonUtils
) {

    fun showDataFromApi(): Observable<DataProperty>? {
        return if (commonUtils.isInternetAvailable())
            repository.getDataFromApi().compose(schedulerProvider.getSchedulersForObservable())
        else {
            commonUtils.toastShow("Please check your internet connection !!!")
            return null
        }
    }
}