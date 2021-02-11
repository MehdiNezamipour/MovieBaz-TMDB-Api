package com.nezamipour.mehdi.moviebaz.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.nezamipour.mehdi.moviebaz.data.local.MovieRepository
import com.nezamipour.mehdi.moviebaz.data.model.Movie
import com.nezamipour.mehdi.moviebaz.network.response.MovieListResponse
import com.nezamipour.mehdi.moviebaz.paging.MoviePagingSource
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel(private val movieRepository: MovieRepository) : ViewModel() {


    private var _searchedMovies: MutableLiveData<MovieListResponse> = MutableLiveData()

    val searchedMovies: LiveData<MovieListResponse>
        get() = _searchedMovies

    val flow: Flow<PagingData<Movie>> = getMovieListStream()
        .map { pagingData -> pagingData.map { it } }


    private fun getMovieListStream(): Flow<PagingData<Movie>> {
        return Pager(PagingConfig(20)) {
            MoviePagingSource(movieRepository)
        }.flow
            .cachedIn(viewModelScope)
    }

    public fun searchBetweenMovies(searchQuery: String) {
        viewModelScope.launch {
            _searchedMovies.value = movieRepository.searchMovies(searchQuery)
        }
    }
}
