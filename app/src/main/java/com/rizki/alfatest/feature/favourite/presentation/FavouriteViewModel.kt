package com.rizki.alfatest.feature.favourite.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizki.alfatest.common.Resource
import com.rizki.alfatest.data.local.entity.FavouriteEntity
import com.rizki.alfatest.domain.usecase.favourite.DeleteFavUseCase
import com.rizki.alfatest.domain.usecase.favourite.FavouriteUseCase
import com.rizki.alfatest.domain.usecase.favourite.GetFavouriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val favouriteUseCase: FavouriteUseCase
) : ViewModel() {

    val getFavouriteResult = MutableLiveData<Resource<List<FavouriteEntity>>>()

    fun getFavourite(){
        viewModelScope.launch {
            favouriteUseCase.getFavourite.invoke().collect(){
                getFavouriteResult.postValue(it)
            }
        }
    }

    fun deleteFavourite(favouriteEntity: FavouriteEntity){
        viewModelScope.launch {
            favouriteUseCase.deleteFavourite(favouriteEntity)
        }
    }
}