package com.rizki.alfatest.feature.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.rizki.alfatest.R
import com.rizki.alfatest.databinding.DialogLoadingBinding

class LoadingDialog(private val context: Context) {

    private val dialog = Dialog(context).apply {
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
        window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        binding.loadingText.text = "Loading..."
    }

    fun show() {
        dialog.show()
    }

    fun dismiss() {
        dialog.dismiss()
    }
}