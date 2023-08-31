package com.rizki.alfatest.app.data.remote.dto

import com.rizki.alfatest.app.domain.mapper.Movies

data class MoviesDto(
    val adult: Boolean,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val vote_average: Double,
    val vote_count: Int
)

fun MoviesDto.toResult(): Movies {
    return Movies(
        adult = adult,
        id = id,
        original_title = original_title,
        overview = overview,
        poster_path = poster_path,
        release_date = release_date
    )
}