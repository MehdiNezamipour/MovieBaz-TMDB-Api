package com.nezamipour.mehdi.moviebaz.data.local

import com.nezamipour.mehdi.moviebaz.data.model.Genre
import com.nezamipour.mehdi.moviebaz.network.api.MovieAppService
import com.nezamipour.mehdi.moviebaz.network.api.Result
import com.nezamipour.mehdi.moviebaz.network.response.GenreListResponse
import com.nezamipour.mehdi.moviebaz.network.response.MovieListResponse

class MovieRepository(private val movieAppService: MovieAppService) {

    var genres: List<Genre>? = emptyList()

    suspend fun getPopularMovies(page: Int): MovieListResponse {
        return when (val result = movieAppService.fetchPopularMovies(page)) {
            is Result.Success -> result.data
            is Result.Error -> MovieListResponse()
        }
    }

    suspend fun discoverByGenre(genresString: String, page: Int): MovieListResponse {
        return when (val result = movieAppService.discoverByGenre(genresString, page)) {
            is Result.Success -> result.data
            is Result.Error -> MovieListResponse()
        }
    }

    suspend fun searchMovies(searchQuery: String): MovieListResponse {
        return when (val result = movieAppService.searchBetweenMovies(searchQuery)) {
            is Result.Success -> result.data
            is Result.Error -> MovieListResponse()
        }
    }

    suspend fun getAllGenre(): GenreListResponse {
        return when (val result = movieAppService.getAllGenre()) {
            is Result.Success -> result.data
            is Result.Error -> GenreListResponse()
        }
    }


}