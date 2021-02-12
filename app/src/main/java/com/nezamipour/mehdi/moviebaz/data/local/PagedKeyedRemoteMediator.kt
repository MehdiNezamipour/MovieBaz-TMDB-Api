package com.nezamipour.mehdi.moviebaz.data.local

import androidx.paging.*
import androidx.room.withTransaction
import com.nezamipour.mehdi.moviebaz.data.database.MovieDataBase
import com.nezamipour.mehdi.moviebaz.data.database.dao.MovieDao
import com.nezamipour.mehdi.moviebaz.data.database.dao.RemoteKeyDao
import com.nezamipour.mehdi.moviebaz.data.database.entity.MovieEntity
import com.nezamipour.mehdi.moviebaz.network.Routes
import com.nezamipour.mehdi.moviebaz.network.api.ApiService
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PagedKeyedRemoteMediator(
    private val title: String,
    private val database: MovieDataBase,
    private val repository: MovieRepository
) : RemoteMediator<Int, MovieEntity>() {

    private val movieDao: MovieDao = database.getMovieDao()
    private val remoteKeyDao: RemoteKeyDao = database.getRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        //TODO
        return MediatorResult.Success(true)

    }
}