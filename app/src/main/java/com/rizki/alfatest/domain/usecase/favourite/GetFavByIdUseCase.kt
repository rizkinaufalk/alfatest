package com.rizki.alfatest.domain.usecase.favourite

import com.rizki.alfatest.common.Resource
import com.rizki.alfatest.data.local.entity.FavouriteEntity
import com.rizki.alfatest.domain.repository.MovieDbRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetFavByIdUseCase @Inject constructor(
    private val dbRepository: MovieDbRepository
) {
    suspend operator fun invoke(id: Int): Resource<FavouriteEntity?> {
        val favourite = dbRepository.getFavMovieById(id)

        return if (favourite != null){
            Resource.Success<FavouriteEntity?>(favourite)
        } else {
            Resource.Error<FavouriteEntity?> ("Favourite Is Empty")
        }
    }
}