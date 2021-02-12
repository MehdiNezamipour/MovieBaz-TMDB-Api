package com.nezamipour.mehdi.moviebaz.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.nezamipour.mehdi.moviebaz.data.local.MovieRepository
import com.nezamipour.mehdi.moviebaz.data.model.Genre
import com.nezamipour.mehdi.moviebaz.data.model.Movie
import com.nezamipour.mehdi.moviebaz.paging.MoviePagingSource
import com.nezamipour.mehdi.moviebaz.utils.StringUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel(private val movieRepository: MovieRepository) : ViewModel() {


    private var _genres: MutableLiveData<List<Genre>> = MutableLiveData()

    var selectedGenres: MutableLiveData<HashMap<String, String>> = MutableLiveData()


    val allGenres: LiveData<List<Genre>>
        get() {
            _genres.value = movieRepository.genres
            return _genres
        }


    fun getPopularMovieFlow(): Flow<PagingData<Movie>> {
        return getMovieListStream(MoviePagingSource(repository = movieRepository, genres = null))
            .map { pagingData -> pagingData.map { it } }
    }

    fun getGenresMovieFlow(): Flow<PagingData<Movie>> {
        return getMovieListStream(
            MoviePagingSource(
                repository = movieRepository,
                genres = selectedGenres.value?.let { StringUtils.createGenresString(it) }
            )
        )
            .map { pagingData -> pagingData.map { it } }
    }


    private fun getMovieListStream(moviePagingSource: MoviePagingSource): Flow<PagingData<Movie>> {
        return Pager(PagingConfig(20)) {
            moviePagingSource
        }.flow
            .cachedIn(viewModelScope)
    }

    fun getAllGenre() {
        viewModelScope.launch {
            movieRepository.genres = movieRepository.getAllGenre().genres
        }
    }

    fun setGenresSelected(genres: HashMap<String, String>) {
        selectedGenres.value = genres
    }

}
