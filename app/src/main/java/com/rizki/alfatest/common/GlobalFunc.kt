package com.rizki.alfatest.common

import java.text.SimpleDateFormat
import java.util.*

object GlobalFunc {

    fun dateConverter(date: String): (String) {

        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date)
            ?.let { SimpleDateFormat("dd MMM YYY", Locale.getDefault()).format(it) } ?: ""
    }
}