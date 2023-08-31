package com.rizki.alfatest.ext.delegate

import android.content.Context
import com.rizki.alfatest.app.feature.dialogs.CustomToastCenter
import com.rizki.alfatest.app.feature.dialogs.LoadingDialog

interface DisplayMessage {
    fun showLoading(context: Context)
    fun hideLoading()
    fun showToastCenter(context: Context, message: String, icon: Int)
}

class DisplayMessageImpl: DisplayMessage {

    private var loadingDialog = LoadingDialog()

    override fun showLoading(context: Context) {
        loadingDialog.show(context)
    }

    override fun hideLoading() {
        loadingDialog.dismiss()
    }

    override fun showToastCenter(context: Context, message: String, icon: Int) {
        val toast = CustomToastCenter()
        toast.showCustomToast(context, message, icon )
    }
}