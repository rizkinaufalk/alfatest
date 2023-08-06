package com.rizki.alfatest.domain.usecase.movie.get_genre

import com.rizki.alfatest.common.Resource
import com.rizki.alfatest.data.remote.dto.toGenre
import com.rizki.alfatest.domain.model.Genres
import com.rizki.alfatest.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetGenreUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    operator fun invoke(): Flow<Resource<List<Genres>>> = flow {
        try {
            emit(Resource.Loading<List<Genres>>())
            val genres = repository.getGenre().map { it.toGenre() }
            emit(Resource.Success<List<Genres>>(genres))
        } catch(e: HttpException) {
            emit(Resource.Error<List<Genres>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<List<Genres>>("Couldn't reach server. Check your internet connection."))
        }
    }
}