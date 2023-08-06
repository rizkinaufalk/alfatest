package com.rizki.alfatest.domain.usecase.movie.get_review

import com.rizki.alfatest.common.Resource
import com.rizki.alfatest.data.remote.dto.toReviews
import com.rizki.alfatest.domain.model.Reviews
import com.rizki.alfatest.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieReviewUseCaSe @Inject constructor(
    private val repository: MovieRepository
) {

    operator fun invoke(movieId: Int): Flow<Resource<List<Reviews>>> = flow {
        try {
            emit(Resource.Loading<List<Reviews>>())
            val genres = repository.getReviews(movieId).map { it.toReviews() }
            emit(Resource.Success<List<Reviews>>(genres))
        } catch(e: HttpException) {
            emit(Resource.Error<List<Reviews>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<List<Reviews>>("Couldn't reach server. Check your internet connection."))
        }
    }
}