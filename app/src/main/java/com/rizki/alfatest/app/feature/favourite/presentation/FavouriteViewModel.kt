package com.rizki.alfatest.app.feature.favourite.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rizki.alfatest.app.common.Resource
import com.rizki.alfatest.app.data.local.entity.FavouriteEntity
import com.rizki.alfatest.app.domain.usecase.favourite.FavouriteUseCase
import com.rizki.alfatest.app.feature.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val favouriteUseCase: FavouriteUseCase
) : BaseViewModel() {

    val getFavouriteResult = MutableLiveData<Resource<List<FavouriteEntity>>>()

    fun getFavourite(){
        viewModelScope.launch {
            favouriteUseCase.getFavourite.invoke()
                .flowOn(Dispatchers.IO)
                .collect { result ->
                    // Post the result on the Main dispatcher
                    withContext(Dispatchers.Main) {
                        getFavouriteResult.postValue(result)
                    }
                }
        }
    }

    fun deleteFavourite(favouriteEntity: FavouriteEntity){
        viewModelScope.launch {
            favouriteUseCase.deleteFavourite(favouriteEntity)
        }
    }
}