package com.nezamipour.mehdi.moviebaz.data.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.nezamipour.mehdi.moviebaz.data.database.entity.MovieEntity

@Dao
interface MovieDao {

    @Insert
    fun insert(movieEntity: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<MovieEntity>)


    @Query("SELECT * FROM movie WHERE title LIKE :query")
    fun pagingSource(query: String): PagingSource<Int, MovieEntity>

    @Query("select * from movie")
    fun getAll(): List<MovieEntity>


    @Query("DELETE FROM movie")
    suspend fun clearAll()

}