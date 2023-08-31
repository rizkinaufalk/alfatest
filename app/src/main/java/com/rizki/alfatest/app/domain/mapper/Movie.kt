package com.rizki.alfatest.app.domain.mapper

import com.rizki.alfatest.app.data.remote.dto.MoviesDto

data class Movie(
    val page: Int,
    val results: List<MoviesDto>
)


