package com.rizki.alfatest.data.remote.dto

import com.rizki.alfatest.domain.mapper.YoutubeVideo

data class VideosDto(
    val id: String,
    val iso_3166_1: String,
    val iso_639_1: String,
    val key: String,
    val name: String,
    val official: Boolean,
    val published_at: String,
    val site: String,
    val size: Int,
    val type: String
)

fun VideosDto.toYoutubeVideo(): YoutubeVideo{
    return YoutubeVideo(
        key = key,
        site = site,
        type = type
    )
}