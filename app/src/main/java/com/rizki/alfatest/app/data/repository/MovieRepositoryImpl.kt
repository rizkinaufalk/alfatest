package com.rizki.alfatest.app.data.repository

import com.rizki.alfatest.app.common.DataSource
import com.rizki.alfatest.app.common.Resource
import com.rizki.alfatest.app.data.remote.MovieApi
import com.rizki.alfatest.app.data.remote.dto.*
import com.rizki.alfatest.app.domain.datasource.MovieDataSource
import com.rizki.alfatest.app.domain.mapper.Movie
import com.rizki.alfatest.app.domain.mapper.Reviews
import com.rizki.alfatest.app.domain.mapper.YoutubeVideo
import com.rizki.alfatest.app.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val source: MovieDataSource
) : MovieRepository {

    override suspend fun getMovie(page: String): Flow<Resource<Movie>> = flow {
        emit(Resource.Success(data = source.getMovie(page).toMovie(), DataSource.REMOTE))
    }

    override suspend fun getMovieByGenre(genre: String, page: Int): Flow<Resource<Movie>> = flow {
        emit(Resource.Success(data = source.getMovieByGenre(genre, page).toMovie(), DataSource.REMOTE))
    }

    override suspend fun getGenre(): Flow<Resource<List<GenresDto>>> = flow {
        emit(Resource.Success(data = source.getGenre(), DataSource.REMOTE))
    }

    override suspend fun getReviews(movieId: Int): Flow<Resource<List<Reviews>>> = flow {
        emit(Resource.Success(data = source.getReviews(movieId).map { it.toReviews() }, DataSource.REMOTE))
    }

    override suspend fun getVideos(movieId: Int?): Flow<Resource<List<VideosDto>>> = flow {
        emit(Resource.Success(data = source.getVideos(movieId), DataSource.REMOTE))
    }
}