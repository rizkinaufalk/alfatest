package com.rizki.alfatest.domain.usecase.favourite

import com.rizki.alfatest.data.local.entity.FavouriteEntity
import com.rizki.alfatest.domain.repository.MovieDbRepository
import javax.inject.Inject

class AddFavouriteUseCase @Inject constructor(
    private val dbRepository: MovieDbRepository
) {
    suspend operator fun invoke(favouriteEntity: FavouriteEntity) {
        return dbRepository.insertFavMovie(favouriteEntity)
    }
}