/**
 * This class fetch the data from server
 */
package com.example.rxjavaretrofitkotlin.repository

import android.util.Log.d
import com.example.rxjavaretrofitkotlin.model.DataProperty
import com.example.rxjavaretrofitkotlin.repository.RetrofitManager.Factory.create
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

// Base URL to get the Json Data
private const val BASE_URL =
    "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/"

class RetrofitManager {
    object Factory {
        fun create(): RetrofitApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(ApiWorker.gsonConverter)
                .client(ApiWorker.client)
                .build()

            return retrofit.create(RetrofitApiService::class.java)
        }
    }

    fun fetchDataFromServer(): Observable<DataProperty>? {
        d("RetrofitManager", "Inside fetchDataFromServer")
        val retrofitCall = create().getPropertiesAsync()
        return retrofitCall.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}