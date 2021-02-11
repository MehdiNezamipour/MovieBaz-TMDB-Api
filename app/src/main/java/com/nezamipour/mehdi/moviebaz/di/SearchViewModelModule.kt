package com.nezamipour.mehdi.moviebaz.di

import com.nezamipour.mehdi.moviebaz.view.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchViewModelModule = module {
    viewModel { SearchViewModel(get()) }
}