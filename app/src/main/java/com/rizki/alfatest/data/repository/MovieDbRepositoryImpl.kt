package com.rizki.alfatest.data.repository

import com.rizki.alfatest.data.local.FavouriteDao
import com.rizki.alfatest.data.local.entity.FavouriteEntity
import com.rizki.alfatest.domain.repository.MovieDbRepository
import kotlinx.coroutines.flow.Flow

class MovieDbRepositoryImpl(
    private val dao: FavouriteDao
) : MovieDbRepository{
    override fun getFavMovie(): Flow<List<FavouriteEntity>> {
        return dao.getFavourite()
    }

    override suspend fun getFavMovieById(id: Int): FavouriteEntity? {
        return dao.getFavouriteById(id)
    }

    override suspend fun insertFavMovie(favouriteEntity: FavouriteEntity) {
        return dao.insertFavourite(favouriteEntity)
    }

    override suspend fun deleteFavMovie(favouriteEntity: FavouriteEntity) {
        return dao.deleteFavourite(favouriteEntity)
    }
}