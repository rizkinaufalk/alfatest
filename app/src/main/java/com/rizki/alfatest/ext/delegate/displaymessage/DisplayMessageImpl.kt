package com.rizki.alfatest.ext.delegate.displaymessage

import android.content.Context
import com.rizki.alfatest.feature.dialogs.CustomToastCenter
import com.rizki.alfatest.feature.dialogs.LoadingDialog

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