package com.rizki.alfatest.feature.dialogs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizki.alfatest.common.Resource
import com.rizki.alfatest.data.local.entity.FavouriteEntity
import com.rizki.alfatest.domain.mapper.YoutubeVideo
import com.rizki.alfatest.domain.usecase.favourite.AddFavouriteUseCase
import com.rizki.alfatest.domain.usecase.favourite.DeleteFavUseCase
import com.rizki.alfatest.domain.usecase.favourite.GetFavByIdUseCase
import com.rizki.alfatest.domain.usecase.movie.get_video.GetVideoByMovieIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailDialogViewmodel  @Inject constructor(
    private val getFavByIdUseCase: GetFavByIdUseCase,
    private val getVideoByMovieIdUseCase: GetVideoByMovieIdUseCase,
    private val addFavouriteUseCase: AddFavouriteUseCase,
    private val deleteFavUseCase: DeleteFavUseCase
)  : ViewModel() {


    val getFavByIdResult = MutableLiveData<Resource<FavouriteEntity?>>()
    val getVideoByMovieIdResult = MutableLiveData<Resource<YoutubeVideo>>()



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

    fun getFavById(movieId: Int){
        viewModelScope.launch {
            getFavByIdResult.postValue(getFavByIdUseCase(movieId) )
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