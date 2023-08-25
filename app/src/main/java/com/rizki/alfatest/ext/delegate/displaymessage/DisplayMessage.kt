package com.rizki.alfatest.ext.delegate.displaymessage

import android.content.Context

interface DisplayMessage {
    fun showLoading(context: Context)
    fun hideLoading()
    fun showToastCenter(context: Context, message: String, icon: Int)
}