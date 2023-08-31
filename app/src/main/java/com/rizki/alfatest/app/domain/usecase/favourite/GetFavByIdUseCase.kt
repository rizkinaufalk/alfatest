package com.rizki.alfatest.app.domain.usecase.favourite

import com.rizki.alfatest.app.common.Resource
import com.rizki.alfatest.app.data.local.entity.FavouriteEntity
import com.rizki.alfatest.app.domain.FlowUseCase
import com.rizki.alfatest.app.domain.repository.MovieDbRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetFavByIdUseCase @Inject constructor(
    private val dbRepository: MovieDbRepository
) : FlowUseCase<Int, FavouriteEntity?>() {
    override suspend fun execute(parameters: Int?): Flow<Resource<FavouriteEntity?>> {
        return dbRepository.getFavMovieById(parameters)
    }
}