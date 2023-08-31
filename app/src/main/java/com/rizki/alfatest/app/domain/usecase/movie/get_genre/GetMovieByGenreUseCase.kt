package com.rizki.alfatest.app.domain.usecase.movie.get_genre

import com.rizki.alfatest.app.common.Resource
import com.rizki.alfatest.app.data.remote.dto.toMovie
import com.rizki.alfatest.app.domain.mapper.Movie
import com.rizki.alfatest.app.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieByGenreUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(genre: String, page: Int): Flow<Resource<Movie>> {
        return repository.getMovieByGenre(genre, page)
    }
}