package com.rizki.alfatest.domain.usecase.favourite

import com.rizki.alfatest.common.Resource
import com.rizki.alfatest.data.local.entity.FavouriteEntity
import com.rizki.alfatest.domain.repository.MovieDbRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavouriteUseCase @Inject constructor(
    private val dbRepository: MovieDbRepository
) {
    operator fun invoke(): Flow<Resource<List<FavouriteEntity>>> = flow {
        try {
            val favourite = dbRepository.getFavMovie().first()
            emit(Resource.Success<List<FavouriteEntity>>(favourite))
        } catch (e: Exception) {
            emit(Resource.Error<List<FavouriteEntity>>("Something wrong"))
        }
    }
}