package com.rizki.alfatest.ext.delegate.displaymessage

import android.app.ProgressDialog
import android.content.Context
import com.rizki.alfatest.MovieApp
import com.rizki.alfatest.feature.dialogs.LoadingDialog

class DisplayMessageImpl: DisplayMessage {

    private var loadingDialog = LoadingDialog()

    override fun showLoading(context: Context) {
        loadingDialog.show(context)
    }

    override fun hideLoading() {
        loadingDialog.dismiss()
    }
}