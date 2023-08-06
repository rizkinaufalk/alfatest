package com.rizki.alfatest.data.repository

import com.rizki.alfatest.data.local.FavouriteDao
import com.rizki.alfatest.data.local.entity.FavouriteEntity
import com.rizki.alfatest.data.remote.MovieApi
import com.rizki.alfatest.data.remote.dto.GenresDto
import com.rizki.alfatest.data.remote.dto.MovieDto
import com.rizki.alfatest.data.remote.dto.ReviewsDto
import com.rizki.alfatest.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi
) : MovieRepository {

    override suspend fun getMovie(page: String): MovieDto {
        return api.getPopularMovie(page)
    }

    override suspend fun getMovieByGenre(genre: String, page: Int): MovieDto {
        return api.getMoviesByGenres(genre, page)
    }

    override suspend fun getGenre(): List<GenresDto> {
        return api.getGenre().genres
    }

    override suspend fun getReviews(movieId: Int): List<ReviewsDto> {
        return api.getMovieReviews(movieId).results
    }
}