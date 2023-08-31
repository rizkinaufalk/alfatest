package com.rizki.alfatest.app.domain.usecase.movie.get_popular

import com.rizki.alfatest.app.common.Resource
import com.rizki.alfatest.app.data.remote.dto.toMovie
import com.rizki.alfatest.app.domain.mapper.Movie
import com.rizki.alfatest.app.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPopularUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    suspend operator fun invoke(page: String): Flow<Resource<Movie>>  {
            return repository.getMovie(page)
    }
}