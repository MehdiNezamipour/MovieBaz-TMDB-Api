package com.nezamipour.mehdi.moviebaz.utils

class ImageUtils {
    // width 185
    companion object {
        fun getImageUrl(imagePath: String) = "https://image.tmdb.org/t/p/w200$imagePath"
        fun getLargeImageUrl (imagePath: String) = "https://image.tmdb.org/t/p/w500$imagePath"
    }



}