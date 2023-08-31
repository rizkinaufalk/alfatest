package com.rizki.alfatest.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.rizki.alfatest.R

class OpeningActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opening)

        nextLayout()
    }

    private fun nextLayout() {
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java).apply {

            })
        }, 1000)
    }
}