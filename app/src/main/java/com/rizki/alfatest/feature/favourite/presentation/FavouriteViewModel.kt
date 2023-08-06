package com.rizki.alfatest.feature.favourite.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizki.alfatest.common.Resource
import com.rizki.alfatest.data.local.entity.FavouriteEntity
import com.rizki.alfatest.domain.usecase.favourite.DeleteFavUseCase
import com.rizki.alfatest.domain.usecase.favourite.GetFavouriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val getFavouriteUseCase: GetFavouriteUseCase,
    private val deleteFavUseCase: DeleteFavUseCase
) : ViewModel() {

    val getFavouriteResult = MutableLiveData<Resource<List<FavouriteEntity>>>()

    fun getFavourite(){
        viewModelScope.launch {
            getFavouriteUseCase().collect(){
                getFavouriteResult.postValue(it)
            }
        }
    }

    fun deleteFavourite(favouriteEntity: FavouriteEntity){
        viewModelScope.launch {
            deleteFavUseCase(favouriteEntity)
        }
    }
}