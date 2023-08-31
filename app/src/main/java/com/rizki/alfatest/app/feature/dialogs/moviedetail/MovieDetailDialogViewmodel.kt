package com.rizki.alfatest.app.feature.dialogs.moviedetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizki.alfatest.app.common.Resource
import com.rizki.alfatest.app.data.local.entity.FavouriteEntity
import com.rizki.alfatest.app.domain.mapper.YoutubeVideo
import com.rizki.alfatest.app.domain.usecase.favourite.FavouriteUseCase
import com.rizki.alfatest.app.domain.usecase.movie.get_video.GetVideoByMovieIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailDialogViewmodel  @Inject constructor(
    private val favouriteUseCase: FavouriteUseCase,
    private val getVideoByMovieIdUseCase: GetVideoByMovieIdUseCase,
)  : ViewModel() {


    val getFavByIdResult = MutableLiveData<Resource<FavouriteEntity?>>()
    val getVideoByMovieIdResult = MutableLiveData<Resource<YoutubeVideo>>()



    fun addFavourite(favouriteEntity: FavouriteEntity){
        viewModelScope.launch {
            favouriteUseCase.addFavourite(favouriteEntity)
        }
    }

    fun deleteFavourite(favouriteEntity: FavouriteEntity){
        viewModelScope.launch {
            favouriteUseCase.deleteFavourite(favouriteEntity)
        }
    }

    fun getFavById(movieId: Int){
        viewModelScope.launch {
            favouriteUseCase.getFavouritebyId.invoke(movieId).collect(){
                getFavByIdResult.postValue(it)
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
}