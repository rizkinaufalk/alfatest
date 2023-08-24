package com.rizki.alfatest.feature.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.rizki.alfatest.R
import com.rizki.alfatest.databinding.DialogLoadingBinding

class LoadingDialog() {
    private var dialog: Dialog? = null

//    private val dialog = Dialog(context).apply {
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        var binding = DataBindingUtil.inflate<DialogLoadingBinding>(
//            LayoutInflater.from(context),
//            R.layout.dialog_loading,
//            null,
//            false
//        )
//        setContentView(binding.root)
//        setCancelable(false)
//        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//
//        binding.loadingText.text = "Loading..."
//    }

    fun show(context: Context) {
        if (dialog != null) {
            dismiss()
        }

        dialog = Dialog(context).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            var binding = DataBindingUtil.inflate<DialogLoadingBinding>(
                LayoutInflater.from(context),
                R.layout.dialog_loading,
                null,
                false
            )
            setContentView(binding.root)
            setCancelable(false)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            binding.loadingText.text = "Loading..."
        }

        dialog!!.show()
    }

    fun dismiss() {
        if (dialog?.isShowing == true) {
            dialog?.dismiss()
        }
    }
}