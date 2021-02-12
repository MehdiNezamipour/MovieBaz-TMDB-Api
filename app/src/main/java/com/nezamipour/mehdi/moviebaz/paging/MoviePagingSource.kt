package com.nezamipour.mehdi.moviebaz.paging

import androidx.paging.PagingSource
import com.nezamipour.mehdi.moviebaz.data.local.MovieRepository
import com.nezamipour.mehdi.moviebaz.data.model.Movie
import com.nezamipour.mehdi.moviebaz.network.api.ApiService
import com.nezamipour.mehdi.moviebaz.network.response.MovieListResponse

class MoviePagingSource(private val repository: MovieRepository, var genres: String?) :
    PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {

            val nextPage = params.key ?: 1
            val movieListResponse: MovieListResponse = if (genres != null) {
                repository.discoverByGenre(genres!!, nextPage)
            } else
                repository.getPopularMovies(nextPage)

            LoadResult.Page(
                data = movieListResponse.results!!,
                prevKey = if (nextPage == 1) null else nextPage.minus(1),
                nextKey = if (nextPage < movieListResponse.totalPages!!)
                    movieListResponse.page?.plus(1) else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


}