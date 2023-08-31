package com.rizki.alfatest.app.domain.repository

import com.rizki.alfatest.app.common.Resource
import com.rizki.alfatest.app.data.remote.dto.GenresDto
import com.rizki.alfatest.app.data.remote.dto.MovieDto
import com.rizki.alfatest.app.data.remote.dto.ReviewsDto
import com.rizki.alfatest.app.data.remote.dto.VideosDto
import com.rizki.alfatest.app.domain.mapper.Movie
import com.rizki.alfatest.app.domain.mapper.Reviews
import com.rizki.alfatest.app.domain.mapper.YoutubeVideo
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovie(page: String): Flow<Resource<Movie>>

    suspend fun getMovieByGenre(genre: String, page: Int):Flow<Resource<Movie>>

    suspend fun getGenre(): Flow<Resource<List<GenresDto>>>

    suspend fun getReviews(movieId: Int): Flow<Resource<List<Reviews>>>

    suspend fun getVideos(movieId: Int?):  Flow<Resource<List<VideosDto>>>
}