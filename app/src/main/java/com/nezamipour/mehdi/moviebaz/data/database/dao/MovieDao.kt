package com.nezamipour.mehdi.moviebaz.data.database.dao

import androidx.room.*
import com.nezamipour.mehdi.moviebaz.data.database.entity.MovieEntity

@Dao
interface MovieDao {

    @Insert
    fun insert(movieEntity: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovieEntities(movieEntities: List<MovieEntity>)

    @Delete
    fun delete(movieEntity: MovieEntity)

    @Update
    fun update(movieEntity: MovieEntity)

    @Query("select * from movie")
    fun getAll(): List<MovieEntity>

}