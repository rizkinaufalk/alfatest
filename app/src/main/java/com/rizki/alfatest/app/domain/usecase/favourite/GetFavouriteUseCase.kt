package com.rizki.alfatest.app.domain.usecase.favourite

import com.rizki.alfatest.app.common.DataSource
import com.rizki.alfatest.app.common.Resource
import com.rizki.alfatest.app.data.local.entity.FavouriteEntity
import com.rizki.alfatest.app.domain.FlowUseCase
import com.rizki.alfatest.app.domain.repository.MovieDbRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavouriteUseCase @Inject constructor(
    private val dbRepository: MovieDbRepository
) : FlowUseCase<Nothing?, List<FavouriteEntity>>(){

    override suspend fun execute(parameters: Nothing?): Flow<Resource<List<FavouriteEntity>>> {
        return dbRepository.getFavMovie()
    }
}