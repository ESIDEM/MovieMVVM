package com.xtremepixel.moviemvvm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xtremepixel.moviemvvm.databinding.ActivityMainBinding
import com.xtremepixel.moviemvvm.movie_details.MovieDetails

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        mainBinding.button.setOnClickListener {
            startActivity(Intent(this, MovieDetails::class.java))
        }
    }
}