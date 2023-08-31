package com.rizki.alfatest.ext.delegate

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import timber.log.Timber

interface EventChange {
    fun registerLifecycleOwner(owner: LifecycleOwner, fragment: Fragment)
}

class EventChangeImpl : EventChange, LifecycleEventObserver{
    private var requireActivity: FragmentActivity? = null
    override fun registerLifecycleOwner(owner: LifecycleOwner, fragment: Fragment) {
        owner.lifecycle.addObserver(this)

        requireActivity = fragment.requireActivity()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when(event) {
            Lifecycle.Event.ON_PAUSE -> {
                hideKeyboard()
                hideLoading()
            }

            Lifecycle.Event.ON_RESUME -> {
            }

            Lifecycle.Event.ON_START -> {
            }
        }
    }

    /** Keyboard */
    fun hideKeyboard() {
        try {
            val view = requireActivity?.currentFocus
            if (view != null) {
                val imm = (requireActivity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?)
                imm!!.hideSoftInputFromWindow(view.windowToken, 0)
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    fun hideLoading() {
//        progressDialogHelper.dismiss()
    }
}