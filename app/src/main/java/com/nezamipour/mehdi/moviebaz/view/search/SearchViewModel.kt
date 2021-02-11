package com.nezamipour.mehdi.moviebaz.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nezamipour.mehdi.moviebaz.data.local.MovieRepository
import com.nezamipour.mehdi.moviebaz.data.model.Movie
import kotlinx.coroutines.launch

class SearchViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val _movies: MutableLiveData<List<Movie>> = MutableLiveData()

    val movies: LiveData<List<Movie>>
        get() {
            return _movies
        }

    fun searchBetweenMovies(queryString: String) {
        viewModelScope.launch {
            _movies.value = movieRepository.searchMovies(queryString).results
        }
    }

}