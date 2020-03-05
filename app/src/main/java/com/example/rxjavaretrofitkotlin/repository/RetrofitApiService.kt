/**
 * This interface use to get data property
 */
package com.example.rxjavaretrofitkotlin.repository

import com.example.rxjavaretrofitkotlin.model.DataProperty
import io.reactivex.Observable
import retrofit2.http.GET

interface RetrofitApiService {
    @GET("facts.json")
    fun getPropertiesAsync():
            Observable<DataProperty>
}