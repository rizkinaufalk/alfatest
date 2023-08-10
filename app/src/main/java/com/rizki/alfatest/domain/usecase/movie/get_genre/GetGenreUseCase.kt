package com.rizki.alfatest.domain.usecase.movie.get_genre

import com.rizki.alfatest.common.Resource
import com.rizki.alfatest.data.remote.dto.GenresDto
import com.rizki.alfatest.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetGenreUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    operator fun invoke(): Flow<Resource<List<GenresDto>>> = flow {
        try {
            emit(Resource.Loading<List<GenresDto>>())
            val genres = repository.getGenre()
            emit(Resource.Success<List<GenresDto>>(genres))
        } catch(e: HttpException) {
            emit(Resource.Error<List<GenresDto>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<List<GenresDto>>("Couldn't reach server. Check your internet connection."))
        }
    }
}