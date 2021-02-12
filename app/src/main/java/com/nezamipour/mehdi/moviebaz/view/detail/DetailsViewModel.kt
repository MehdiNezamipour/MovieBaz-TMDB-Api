package com.nezamipour.mehdi.moviebaz.view.detail

import androidx.lifecycle.ViewModel
import com.nezamipour.mehdi.moviebaz.data.local.MovieRepository

class DetailsViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun extractMovieGenres(genre_Ids: List<Int>): String {
        val allGenres = movieRepository.genres
        var answer = ""
        genre_Ids.forEach {
            allGenres?.forEach { it1 ->
                if (it1.id == it)
                    answer = answer.plus(it1.name).plus("     ")
            }
        }
        return answer
    }
}