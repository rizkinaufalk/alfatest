package com.rizki.alfatest.app.domain.repository

import com.rizki.alfatest.app.common.Resource
import com.rizki.alfatest.app.data.local.entity.FavouriteEntity
import kotlinx.coroutines.flow.Flow

interface MovieDbRepository {

    fun getFavMovie(): Flow<Resource<List<FavouriteEntity>>>

    suspend fun getFavMovieById(id: Int?): Flow<Resource<FavouriteEntity?>>

    suspend fun insertFavMovie(favouriteEntity: FavouriteEntity)

    suspend fun deleteFavMovie(favouriteEntity: FavouriteEntity)
}