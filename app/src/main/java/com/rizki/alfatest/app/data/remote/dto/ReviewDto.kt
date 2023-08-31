package com.rizki.alfatest.app.data.remote.dto

data class ReviewDto(
    val id: Int,
    val page: Int,
    val results: List<ReviewsDto>,
    val total_pages: Int,
    val total_results: Int
)