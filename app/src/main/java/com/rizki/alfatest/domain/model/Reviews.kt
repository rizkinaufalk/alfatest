package com.rizki.alfatest.domain.model

data class Reviews(
    val content: String,
    val created_at: String,
    val updated_at: String?,
    val avatar_path: String?,
    val username: String
)
