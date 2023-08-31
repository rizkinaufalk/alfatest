package com.rizki.alfatest.app.feature.home.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rizki.alfatest.app.common.Resource
import com.rizki.alfatest.app.data.remote.dto.GenresDto
import com.rizki.alfatest.app.domain.mapper.Movie
import com.rizki.alfatest.app.domain.usecase.movie.get_genre.GetGenreUseCase
import com.rizki.alfatest.app.domain.usecase.movie.get_genre.GetMovieByGenreUseCase
import com.rizki.alfatest.app.domain.usecase.movie.get_popular.GetPopularUseCase
import com.rizki.alfatest.app.feature.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMovieUseCase: GetPopularUseCase,
    private val getGenreUseCase: GetGenreUseCase,
    private val getMovieByGenreUseCase: GetMovieByGenreUseCase,
) : BaseViewModel() {

    val getMovieResult = MutableLiveData<Resource<Movie>>()
    val getGenreResult = MutableLiveData<Resource<List<GenresDto>>>()
    val getMovieByGenreResult = MutableLiveData<Resource<Movie>>()

    fun getMovie(page: String) {
        viewModelScope.launch {
            getMovieUseCase( page).collect {
                getMovieResult.postValue(it)
            }
        }
    }

    fun getMovieByGenre(genre: String, page: Int) {
        viewModelScope.launch {
            getMovieByGenreUseCase( genre, page).collect {
                getMovieByGenreResult.postValue(it)
            }
        }
    }

    fun getGenre(){
        viewModelScope.launch {
            getGenreUseCase().collect(){
                getGenreResult.postValue(it)
            }
        }
    }

}