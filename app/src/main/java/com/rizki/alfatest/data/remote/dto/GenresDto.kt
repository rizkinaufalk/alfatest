package com.rizki.alfatest.data.remote.dto

import com.rizki.alfatest.domain.model.Genres

data class GenresDto(
    val id: Int,
    val name: String
)

//fun GenresDto.toGenre(): Genres{
//    return Genres(
//        id = id,
//        name = name
//    )
//}