package com.rizki.alfatest.app.domain.mapper

data class Reviews(
    val content: String,
    val created_at: String,
    val updated_at: String?,
    val avatar_path: String?,
    val username: String
)
