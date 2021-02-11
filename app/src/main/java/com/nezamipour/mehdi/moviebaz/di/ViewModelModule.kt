package com.nezamipour.mehdi.moviebaz.di

import com.nezamipour.mehdi.moviebaz.view.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
}
