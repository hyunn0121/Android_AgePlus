package com.example.finalproject_ageplus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.finalproject_ageplus.databinding.ActivityDetailBinding
import com.example.finalproject_ageplus.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}