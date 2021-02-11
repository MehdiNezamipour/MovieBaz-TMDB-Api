package com.nezamipour.mehdi.moviebaz.network.api

import com.nezamipour.mehdi.moviebaz.data.model.Result
import com.nezamipour.mehdi.moviebaz.network.Routes
import com.nezamipour.mehdi.moviebaz.network.response.MovieListResponse

class MovieAppService(private val apiService: ApiService) : BaseService() {


    suspend fun fetchPopularMovies(page: Int): Result<MovieListResponse> {
        return createCall { apiService.getPopular(Routes.API_KEY, page) }
    }
}