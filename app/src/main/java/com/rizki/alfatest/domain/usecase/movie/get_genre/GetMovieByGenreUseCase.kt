package com.rizki.alfatest.domain.usecase.movie.get_genre

import com.rizki.alfatest.common.Resource
import com.rizki.alfatest.data.remote.dto.toMovie
import com.rizki.alfatest.domain.model.Movie
import com.rizki.alfatest.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieByGenreUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    operator fun invoke(genre: String, page: Int): Flow<Resource<Movie>> = flow {
        try {
            emit(Resource.Loading())
            val movie = repository.getMovieByGenre(genre, page).toMovie()
            emit(Resource.Success<Movie>(movie))
        } catch (e: HttpException) {
            emit(Resource.Error<Movie>(e.localizedMessage ?: "An Unexpected error occured"))
        } catch (e: IOException){
            emit(Resource.Error<Movie>("Check your internet connection"))
        }
    }
}