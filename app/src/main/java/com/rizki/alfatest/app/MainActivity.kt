package com.rizki.alfatest.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rizki.alfatest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

       /** You don't have to navigate to first fragment since it already defined in navgraph as your first destination**/
    }
}