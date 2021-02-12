package com.nezamipour.mehdi.moviebaz.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nezamipour.mehdi.moviebaz.data.database.dao.MovieDao
import com.nezamipour.mehdi.moviebaz.data.database.dao.RemoteKeyDao
import com.nezamipour.mehdi.moviebaz.data.database.entity.MovieEntity
import com.nezamipour.mehdi.moviebaz.data.database.entity.RemoteKey

@Database(
    entities = [MovieEntity::class, RemoteKey::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDataBase : RoomDatabase() {

    companion object {
        fun create(context: Context, useInMemory: Boolean): MovieDataBase {
            val databaseBuilder = if (useInMemory) {
                Room.inMemoryDatabaseBuilder(context, MovieDataBase::class.java)
            } else {
                Room.databaseBuilder(context, MovieDataBase::class.java, "movie.db")
            }
            return databaseBuilder
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun getMovieDao(): MovieDao
    abstract fun getRemoteKeysDao(): RemoteKeyDao
}