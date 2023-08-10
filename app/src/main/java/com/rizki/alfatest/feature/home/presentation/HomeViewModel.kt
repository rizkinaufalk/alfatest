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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMovieUseCase: GetPopularUseCase,
    private val getGenreUseCase: GetGenreUseCase,
    private val getMovieByGenreUseCase: GetMovieByGenreUseCase,
    private val addFavouriteUseCase: AddFavouriteUseCase,
    private val deleteFavUseCase: DeleteFavUseCase,
    private val getFavByIdUseCase: GetFavByIdUseCase,
    private val getVideoByMovieIdUseCase: GetVideoByMovieIdUseCase
) : ViewModel() {

    val getMovieResult = MutableLiveData<Resource<Movie>>()
    val getGenreResult = MutableLiveData<Resource<List<GenresDto>>>()
    val getMovieByGenreResult = MutableLiveData<Resource<Movie>>()
    val getFavByIdResult = MutableLiveData<Resource<FavouriteEntity?>>()
    val getVideoByMovieIdResult = MutableLiveData<Resource<List<YoutubeVideo>>>()

    fun getFavById(movieId: Int){
        viewModelScope.launch {
            getFavByIdResult.postValue(getFavByIdUseCase(movieId) )
        }
    }

    fun addFavourite(favouriteEntity: FavouriteEntity){
        viewModelScope.launch {
            addFavouriteUseCase(favouriteEntity)
        }
    }

    fun deleteFavourite(favouriteEntity: FavouriteEntity){
        viewModelScope.launch {
            deleteFavUseCase(favouriteEntity)
        }
    }

    fun getMovie(page: String) {
        viewModelScope.launch {
            getMovieUseCase( page).collect {
                getMovieResult.postValue(it)
            }
        }
    }

    fun getVideoByMovieId(movieId: Int) {
        viewModelScope.launch {
            getVideoByMovieIdUseCase(movieId).collect {
                getVideoByMovieIdResult.postValue(it)
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