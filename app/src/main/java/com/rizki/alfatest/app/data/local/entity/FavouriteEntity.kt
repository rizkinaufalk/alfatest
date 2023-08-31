package com.rizki.alfatest.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavouriteEntity(
    @PrimaryKey val id: Int? = null,
    val original_title: String,
    val overview: String,
    val poster_path: String
)
