package com.rizki.alfatest.app.feature.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizki.alfatest.app.common.SingleLiveEvent

abstract class BaseViewModel : ViewModel() {

    // RxJava
    enum class UiMode { INITIATE, ON_PROGRESS, SUCCESS, ERROR }

    private val mUiMode = MutableLiveData(UiMode.INITIATE)
    val uiMode: LiveData<UiMode> = mUiMode

    val isLoading: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val showToast: SingleLiveEvent<String> = SingleLiveEvent()

}