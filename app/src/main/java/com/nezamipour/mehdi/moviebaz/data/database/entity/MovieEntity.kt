package com.nezamipour.mehdi.moviebaz.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "movie")
data class MovieEntity(

    @PrimaryKey
    var id: Int,
    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "overview")
    var overview: String,

    @SerializedName("genre_ids")
    var genreIds: List<Int>,

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    var posterPath: String,

    @SerializedName("vote_count")
    var voteCount: Int,

    @SerializedName("vote_average")
    var voteAverage: Float,

    var popularity: Float,

    @SerializedName("original_language")
    var originalLanguage: String,

    @SerializedName("original_title")
    var originalTitle: String,

    @SerializedName("backdrop_path")
    var backdropPath: String,

    var adult: Boolean,

    @SerializedName("release_date")
    var releaseDate: String,

) {
    // to be consistent w/ changing backend order, we need to keep a data like this
    var indexInResponse: Int = -1
}