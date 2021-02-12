package com.nezamipour.mehdi.moviebaz.utils

class StringUtils {

    companion object {
        fun createGenresString(genres: HashMap<String, String>): String {
            var answer = ""
            genres.forEach {
                answer = answer.plus(it.value).plus(",")
            }
            return answer
        }

    }
}