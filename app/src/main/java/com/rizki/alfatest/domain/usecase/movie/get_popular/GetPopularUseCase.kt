package com.rizki.alfatest.domain.usecase.movie.get_popular

import com.rizki.alfatest.common.Resource
import com.rizki.alfatest.data.remote.dto.toMovie
import com.rizki.alfatest.domain.mapper.Movie
import com.rizki.alfatest.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPopularUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    operator fun invoke(page: String): Flow<Resource<Movie>> = flow {
        try {
            emit(Resource.Loading())
            val movie = repository.getMovie(page).toMovie()
            emit(Resource.Success<Movie>(movie))
        } catch (e: HttpException) {
            emit(Resource.Error<Movie>(e.localizedMessage ?: "An Unexpected error occured"))
        } catch (e: IOException){
            emit(Resource.Error<Movie>("Check your internet connection"))
        }
    }
}