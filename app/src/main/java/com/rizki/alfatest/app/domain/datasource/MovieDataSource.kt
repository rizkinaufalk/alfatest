package com.rizki.alfatest.app.domain.datasource

import com.rizki.alfatest.app.data.remote.dto.GenresDto
import com.rizki.alfatest.app.data.remote.dto.MovieDto
import com.rizki.alfatest.app.data.remote.dto.ReviewsDto
import com.rizki.alfatest.app.data.remote.dto.VideosDto
import com.rizki.alfatest.app.domain.mapper.YoutubeVideo

interface MovieDataSource {
    suspend fun getMovie(page: String): MovieDto

    suspend fun getMovieByGenre(genre: String, page: Int): MovieDto

    suspend fun getGenre(): List<GenresDto>

    suspend fun getReviews(movieId: Int): List<ReviewsDto>

    suspend fun getVideos(movieId: Int?): List<VideosDto>
}