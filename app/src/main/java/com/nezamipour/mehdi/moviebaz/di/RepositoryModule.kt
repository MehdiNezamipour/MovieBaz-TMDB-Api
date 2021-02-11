package com.nezamipour.mehdi.moviebaz.di

import com.nezamipour.mehdi.moviebaz.data.local.MovieRepository
import com.nezamipour.mehdi.moviebaz.network.api.MovieAppService
import org.koin.dsl.module

val repositoryModule = module {
    single { createRepository(get()) }
}

fun createRepository(
    movieAppService: MovieAppService
) : MovieRepository = MovieRepository(movieAppService)