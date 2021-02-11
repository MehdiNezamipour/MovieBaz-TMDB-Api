package com.nezamipour.mehdi.moviebaz.di

import com.nezamipour.mehdi.moviebaz.view.detail.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val detailViewModelModule = module {
    viewModel { DetailsViewModel() }
}
