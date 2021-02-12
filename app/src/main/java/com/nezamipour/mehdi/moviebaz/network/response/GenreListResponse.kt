package com.nezamipour.mehdi.moviebaz.network.response

import com.google.gson.annotations.SerializedName
import com.nezamipour.mehdi.moviebaz.data.model.Genre

class GenreListResponse {
    @SerializedName("genres")
    val genres: List<Genre>? = null
}