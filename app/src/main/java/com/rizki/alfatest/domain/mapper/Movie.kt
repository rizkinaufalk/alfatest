package com.rizki.alfatest.domain.mapper

import com.rizki.alfatest.data.remote.dto.MoviesDto

data class Movie(
    val page: Int,
    val results: List<MoviesDto>
)


