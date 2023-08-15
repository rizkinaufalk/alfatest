package com.rizki.alfatest.data.repository

import com.rizki.alfatest.data.remote.MovieApi
import com.rizki.alfatest.data.remote.dto.*
import com.rizki.alfatest.domain.mapper.YoutubeVideo
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

    // In this case i want to filter only youtube videos because the api provide any other type which not only youtube
    override suspend fun getVideos(movieId: Int): YoutubeVideo {
        // filter the result from api with the desired type after that pass it to wrapper with map

        val item = api.getVideo(movieId).results.filter {
            it.site == "YouTube" && it.type == "Trailer"
        }.map {
            it
        }.first().toYoutubeVideo()
        return item
    }
}