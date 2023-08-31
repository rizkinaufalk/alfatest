package com.rizki.alfatest.app.domain.usecase.movie.get_genre

import com.rizki.alfatest.app.common.Resource
import com.rizki.alfatest.app.data.remote.dto.GenresDto
import com.rizki.alfatest.app.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetGenreUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    suspend operator fun invoke(): Flow<Resource<List<GenresDto>>> {
        return repository.getGenre()
    }
}