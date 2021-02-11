package com.nezamipour.mehdi.moviebaz

import android.app.Application
import com.nezamipour.mehdi.moviebaz.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieBazApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MovieBazApp)
            modules(listOf(networkModule, repositoryModule, viewModelModule, detailViewModelModule, searchViewModelModule))
        }
    }
}