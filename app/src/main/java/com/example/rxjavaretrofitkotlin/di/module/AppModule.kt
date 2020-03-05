package com.example.rxjavaretrofitkotlin.di.module

import android.app.Application
import com.example.rxjavaretrofitkotlin.repository.RetrofitApiService
import com.example.rxjavaretrofitkotlin.utils.CommonUtils
import com.example.rxjavaretrofitkotlin.utils.SchedulerProvider
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val BASE_URL =
    "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/"


//Provide all the app level dependency here
@Module
class AppModule {

    @Provides
    fun providesCommonUtilsRepository(app: Application): CommonUtils {
        return CommonUtils(app)
    }

    @Provides
    @Singleton
    fun provideSchedulerProvider() = SchedulerProvider(
        Schedulers.io(),
        AndroidSchedulers.mainThread()
    )

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .disableHtmlEscaping()
            .create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC

        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(gson: Gson, okHttpClient: OkHttpClient): RetrofitApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build().create(RetrofitApiService::class.java)
    }

}