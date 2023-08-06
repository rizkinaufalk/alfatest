package com.rizki.alfatest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rizki.alfatest.data.local.entity.FavouriteEntity

@Database(
    entities = [FavouriteEntity::class],
    version = 1
)
abstract class MovieDb: RoomDatabase() {

    abstract val dao: FavouriteDao
}