package com.rizki.alfatest.app.domain.usecase.movie.get_review

import com.rizki.alfatest.app.common.Resource
import com.rizki.alfatest.app.data.remote.dto.toReviews
import com.rizki.alfatest.app.domain.mapper.Reviews
import com.rizki.alfatest.app.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieReviewUseCaSe @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Flow<Resource<List<Reviews>>> {
        return repository.getReviews(movieId)
    }
}