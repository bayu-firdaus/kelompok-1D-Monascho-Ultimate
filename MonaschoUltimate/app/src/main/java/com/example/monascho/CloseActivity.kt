package com.example.monascho

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.monascho.databinding.ActivityMainBinding

class CloseActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        supportActionBar!!.hide()
        finish()
    }
}