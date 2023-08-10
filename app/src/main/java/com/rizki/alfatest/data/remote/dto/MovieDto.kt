package com.rizki.alfatest.data.remote.dto

import com.rizki.alfatest.domain.mapper.Movie

data class MovieDto(
    val page: Int,
    val results: List<MoviesDto>,
    val total_pages: Int,
    val total_results: Int
)

fun MovieDto.toMovie(): Movie{
    return Movie(
        page = page,
        results = results
    )
}