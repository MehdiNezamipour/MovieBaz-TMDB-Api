package com.nezamipour.mehdi.moviebaz.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.nezamipour.mehdi.moviebaz.network.Routes
import com.nezamipour.mehdi.moviebaz.network.api.ApiService
import com.nezamipour.mehdi.moviebaz.network.api.MovieAppService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Route
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { retrofit() }
    single { apiService(get()) }
    single { createMovieAppService(get()) }
}

fun createMovieAppService(
    apiService: ApiService
): MovieAppService = MovieAppService(apiService)

fun apiService(retrofit: Retrofit): ApiService =
    retrofit.create(ApiService::class.java)

fun retrofit(): Retrofit = Retrofit.Builder()
    .baseUrl(Routes.BASE_URL)
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .addConverterFactory(GsonConverterFactory.create())
    .build()
