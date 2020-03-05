package com.example.rxjavaretrofitkotlin.viewmodel

import android.util.Log.d
import androidx.lifecycle.ViewModel
import com.example.rxjavaretrofitkotlin.model.DataProperty
import com.example.rxjavaretrofitkotlin.repository.RetrofitManager
import com.example.samplewithall.utils.CommonUtils
import io.reactivex.Observable
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val retrofitManager: RetrofitManager, private val commonUtils: CommonUtils
) : ViewModel() {

    fun getDataFromServer(): Observable<DataProperty>? {
        d("MainActivityViewModel", "ViewModel Get Data from server")
        return if (commonUtils.isInternetAvailable())
            retrofitManager.fetchDataFromServer()
        else {
            d("MainActivityViewModel", "Internet is not available")
            commonUtils.toastShow("Please check your internet connection !!!")
            return null
        }
    }
}