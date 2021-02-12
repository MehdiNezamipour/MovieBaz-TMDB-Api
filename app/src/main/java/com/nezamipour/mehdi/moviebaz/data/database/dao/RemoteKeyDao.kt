package com.nezamipour.mehdi.moviebaz.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nezamipour.mehdi.moviebaz.data.database.entity.RemoteKey

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keys: RemoteKey)

    @Query("SELECT * FROM remote_keys WHERE title = :title")
    suspend fun remoteKeyByPost(title: String): RemoteKey

    @Query("DELETE FROM remote_keys WHERE title = :title")
    suspend fun deleteBySubreddit(title: String)
}