package com.rizki.alfatest.domain.mapper

data class Movies(
    val adult: Boolean,
    val id: Int,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val release_date: String,
) {
    fun clear(){
        Movies(false, 0,"","","","")
    }
}