package com.nezamipour.mehdi.moviebaz.network.api

import com.nezamipour.mehdi.moviebaz.network.Routes
import com.nezamipour.mehdi.moviebaz.network.response.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //each page return 10 movies
    @GET(Routes.POPULAR)
    suspend fun getPopular(
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): Response<MovieListResponse>

    @GET(Routes.MOVIE_DETAIL)
    suspend fun getMovieDetails(
        @Query("api_key") api_key: String,
        @Path("movie_id") movie_id: Int
    )

    @GET(Routes.SEARCH)
    suspend fun searchMovie(
        @Query("api_key") api_key: String,
        @Query("query") searchQuery: String
    ): Response<MovieListResponse>

    @GET(Routes.MOVIE_DISCOVER)
    suspend fun discoverByGenre(
        @Query("api_key") api_key: String,
        @Query("with_genres") genres: String
    )


    //get all genres
    @GET(Routes.MOVIE_GENRES)
    suspend fun getAllGenres(@Query("api_key") api_key: String)

}