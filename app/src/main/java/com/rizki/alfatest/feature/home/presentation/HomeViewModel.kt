package com.rizki.alfatest.feature.home.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizki.alfatest.common.Resource
import com.rizki.alfatest.data.local.entity.FavouriteEntity
import com.rizki.alfatest.data.remote.dto.GenresDto
import com.rizki.alfatest.domain.mapper.Movie
import com.rizki.alfatest.domain.mapper.YoutubeVideo
import com.rizki.alfatest.domain.usecase.favourite.AddFavouriteUseCase
import com.rizki.alfatest.domain.usecase.favourite.DeleteFavUseCase
import com.rizki.alfatest.domain.usecase.favourite.GetFavByIdUseCase
import com.rizki.alfatest.domain.usecase.movie.get_genre.GetGenreUseCase
import com.rizki.alfatest.domain.usecase.movie.get_genre.GetMovieByGenreUseCase
import com.rizki.alfatest.domain.usecase.movie.get_popular.GetPopularUseCase
import com.rizki.alfatest.domain.usecase.movie.get_video.GetVideoByMovieIdUseCase
import com.rizki.alfatest.feature.base.BaseViewModel
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