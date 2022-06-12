package com.shivamsatija.githubtrendingrepos.di.module

import android.app.Application
import android.content.Context
import com.shivamsatija.githubtrendingrepos.BuildConfig
import com.shivamsatija.githubtrendingrepos.data.remote.RepositoryService
import com.shivamsatija.githubtrendingrepos.di.ApplicationContext
import com.shivamsatija.githubtrendingrepos.di.BaseUrl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
open class ApplicationModule(
    private val application: Application
) {

    @Provides
    @ApplicationContext
    fun provideApplicationContext(): Context = application.applicationContext

    @Provides
    @BaseUrl
    open fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        @BaseUrl baseUrl: String,
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideRepositoryService(
        retrofit: Retrofit
    ): RepositoryService {
        return retrofit.create(RepositoryService::class.java)
    }
}