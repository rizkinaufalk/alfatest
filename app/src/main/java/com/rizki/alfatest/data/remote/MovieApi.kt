package com.rizki.alfatest.data.remote

import com.rizki.alfatest.data.remote.dto.GenreDto
import com.rizki.alfatest.data.remote.dto.MovieDto
import com.rizki.alfatest.data.remote.dto.ReviewDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular?api_key=$API_KEY")
    suspend fun getPopularMovie(
        @Query("page") page: String
    ) : MovieDto

    @GET("genre/movie/list?api_key=$API_KEY")
    suspend fun getGenre(): GenreDto

    @GET("discover/movie?api_key=$API_KEY")
    suspend fun getMoviesByGenres(
        @Query("with_genres") genres: String,
        @Query("page") page:Int
    ): MovieDto

    @GET("movie/{id}/reviews?api_key=$API_KEY")
    suspend fun getMovieReviews(
        @Path("id") movieId: Int
    ): ReviewDto

    companion object{
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
        const val API_KEY = "7fea831e88cc5359f9c86b0f6467792b"
    }
}