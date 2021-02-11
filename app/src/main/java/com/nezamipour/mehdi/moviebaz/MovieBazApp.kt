package com.nezamipour.mehdi.moviebaz

import android.app.Application
import com.nezamipour.mehdi.moviebaz.di.detailViewModelModule
import com.nezamipour.mehdi.moviebaz.di.networkModule
import com.nezamipour.mehdi.moviebaz.di.repositoryModule
import com.nezamipour.mehdi.moviebaz.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieBazApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MovieBazApp)
            modules(listOf(networkModule, repositoryModule, viewModelModule, detailViewModelModule))
        }
    }
}