package com.rizki.alfatest.domain.repository

import com.rizki.alfatest.data.remote.dto.GenresDto
import com.rizki.alfatest.data.remote.dto.MovieDto
import com.rizki.alfatest.data.remote.dto.ReviewsDto

interface MovieRepository {

    suspend fun getMovie(page: String): MovieDto

    suspend fun getMovieByGenre(genre: String, page: Int): MovieDto

    suspend fun getGenre(): List<GenresDto>

    suspend fun getReviews(movieId: Int): List<ReviewsDto>
}