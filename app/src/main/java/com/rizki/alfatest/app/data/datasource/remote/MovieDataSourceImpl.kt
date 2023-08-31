package com.rizki.alfatest.app.data.datasource.remote

import com.rizki.alfatest.app.data.remote.MovieApi
import com.rizki.alfatest.app.data.remote.dto.*
import com.rizki.alfatest.app.domain.datasource.MovieDataSource
import com.rizki.alfatest.app.domain.mapper.YoutubeVideo
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(
    private val api: MovieApi
) : MovieDataSource{

    override suspend fun getMovie(page: String): MovieDto = api.getPopularMovie(page)

    override suspend fun getMovieByGenre(genre: String, page: Int): MovieDto = api.getMoviesByGenres(genre, page)

    override suspend fun getGenre(): List<GenresDto>  = api.getGenre().genres

    override suspend fun getReviews(movieId: Int): List<ReviewsDto> = api.getMovieReviews(movieId).results

    override suspend fun getVideos(movieId: Int?): List<VideosDto> = api.getVideo(movieId).results
}