package com.rizki.alfatest.app.data.repository

import com.rizki.alfatest.app.common.DataSource
import com.rizki.alfatest.app.common.Resource
import com.rizki.alfatest.app.data.local.FavouriteDao
import com.rizki.alfatest.app.data.local.entity.FavouriteEntity
import com.rizki.alfatest.app.domain.repository.MovieDbRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieDbRepositoryImpl(
    private val localSource: FavouriteDao
) : MovieDbRepository{
    override fun getFavMovie(): Flow<Resource<List<FavouriteEntity>>> = flow {
        emit(Resource.Success(data = localSource.getFavourite(), DataSource.CACHE))
    }

    override suspend fun getFavMovieById(id: Int?): Flow<Resource<FavouriteEntity?>> = flow{
        emit(Resource.Success(data = localSource.getFavouriteById(id), DataSource.CACHE))
    }

    override suspend fun insertFavMovie(favouriteEntity: FavouriteEntity) {
        return localSource.insertFavourite(favouriteEntity)
    }

    override suspend fun deleteFavMovie(favouriteEntity: FavouriteEntity) {
        return localSource.deleteFavourite(favouriteEntity)
    }
}