package com.nezamipour.mehdi.moviebaz.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey(@PrimaryKey val title: String, val nextKey: String?)