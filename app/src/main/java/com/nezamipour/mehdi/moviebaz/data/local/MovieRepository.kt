package com.nezamipour.mehdi.moviebaz.data.local

import com.nezamipour.mehdi.moviebaz.data.model.Result
import com.nezamipour.mehdi.moviebaz.network.api.ApiService
import com.nezamipour.mehdi.moviebaz.network.api.MovieAppService
import com.nezamipour.mehdi.moviebaz.network.response.MovieListResponse

class MovieRepository(private val movieAppService: MovieAppService) {

    suspend fun getPopularMovies(page: Int): MovieListResponse {
        return when (val result = movieAppService.fetchPopularMovies(page)) {
            is Result.Success -> result.data
            is Result.Error -> throw result.error
        }
    }
}