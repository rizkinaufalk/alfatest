package com.rizki.alfatest.feature.review.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizki.alfatest.common.Resource
import com.rizki.alfatest.domain.mapper.Reviews
import com.rizki.alfatest.domain.usecase.movie.get_review.GetMovieReviewUseCaSe
import com.rizki.alfatest.feature.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val getMovieReviewUseCaSe: GetMovieReviewUseCaSe
) : BaseViewModel() {

    val getReviewResult = MutableLiveData<Resource<List<Reviews>>>()

    fun getReview(movieId: Int) {
        viewModelScope.launch {
            getMovieReviewUseCaSe(movieId).collect {
                getReviewResult.postValue(it)
            }
        }
    }

}