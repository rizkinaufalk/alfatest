package com.rizki.alfatest.app.feature.dialogs

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

class SpinnerHelper(private val context: Context, private val spinner: Spinner) {

    fun <T> setupSpinner(layout: Int,  itemList: List<T>, onItemSelectedListener: (T, Int) -> Unit) {
        val adapter = ArrayAdapter(context, layout, itemList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                onItemSelectedListener.invoke(itemList[position], position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do something when nothing is selected
            }
        }
    }
}