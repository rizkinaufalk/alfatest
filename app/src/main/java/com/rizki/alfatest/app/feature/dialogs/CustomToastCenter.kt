package com.rizki.alfatest.app.feature.dialogs

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import com.rizki.alfatest.databinding.CustomToastCenterBinding

class CustomToastCenter {

    fun showCustomToast(context: Context, message: String, icon: Int) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = CustomToastCenterBinding.inflate(inflater)

        binding.tvContent.text = message
        binding.ivIcon.setImageResource(icon)

        val toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.view = binding.root
        toast.show()
    }
}