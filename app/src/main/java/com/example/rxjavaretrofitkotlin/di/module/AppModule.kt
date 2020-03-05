package com.example.rxjavaretrofitkotlin.di.module

import android.app.Application
import com.example.rxjavaretrofitkotlin.repository.RetrofitManager
import com.example.samplewithall.utils.CommonUtils
import dagger.Module
import dagger.Provides

//Provide all the app level dependency here
@Module
class AppModule {

    @Provides
    fun providesRepository(): RetrofitManager {
        return RetrofitManager()
    }

    @Provides
    fun providesCommonUtilsRepository(app: Application): CommonUtils {
        return CommonUtils(app)
    }
}