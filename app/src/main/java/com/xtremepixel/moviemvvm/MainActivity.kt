package com.xtremepixel.moviemvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xtremepixel.moviemvvm.adapter.MovieAdapter
import com.xtremepixel.moviemvvm.data.Resource
import com.xtremepixel.moviemvvm.databinding.ActivityMainBinding
import com.xtremepixel.moviemvvm.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MovieViewModel>()
    lateinit var movieRecyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var errorTextView: TextView
    lateinit var activityMainBinding: ActivityMainBinding
    private val movieListAdapter = MovieAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        movieRecyclerView = activityMainBinding.moviesList
        progressBar = activityMainBinding.loadingView
        errorTextView = activityMainBinding.listError
        movieRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = movieListAdapter
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.movieData.observe(this, Observer { result ->

            when (result.status) {
                Resource.Status.SUCCESS -> {
                    result.data?.results?.let { list ->
                        movieListAdapter.updateCountries(list)
                    }
                    progressBar.visibility = View.GONE
                }

                Resource.Status.ERROR -> {
                    result.message?.let {
                        errorTextView.visibility = View.VISIBLE
                    }
                    progressBar.visibility = View.GONE
                }

                Resource.Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
            }

        })
    }
}