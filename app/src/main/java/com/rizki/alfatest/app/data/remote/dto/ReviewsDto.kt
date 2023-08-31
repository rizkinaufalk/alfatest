package com.rizki.alfatest.app.data.remote.dto

import com.rizki.alfatest.app.domain.mapper.Reviews

data class ReviewsDto(
    val author: String,
    val author_details: AuthorDto,
    val content: String,
    val created_at: String,
    val id: String,
    val updated_at: String?,
    val url: String
)



fun ReviewsDto.toReviews(): Reviews{
    return Reviews(
        content = content,
        created_at = created_at,
        updated_at = updated_at,
        avatar_path = author_details.avatar_path,
        username = author_details.username
    )
}