package com.example.rxjavaretrofitkotlin.repository

import com.example.rxjavaretrofitkotlin.model.DataProperty
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val retrofitApiService: RetrofitApiService) {

    fun getDataFromApi(): Observable<DataProperty> = retrofitApiService.getPropertiesAsync()

}