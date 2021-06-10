package com.xtremepixel.moviemvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xtremepixel.moviemvvm.adapter.MovieAdapter
import com.xtremepixel.moviemvvm.databinding.ActivityMainBinding
import com.xtremepixel.moviemvvm.viewmodel.MovieViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MovieViewModel
    lateinit var movieRecyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var errorTextView: TextView
    lateinit var activityMainBinding: ActivityMainBinding
    private val movieListAdapter = MovieAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        viewModel.refresh()
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
        viewModel.movies.observe(this, Observer {countries ->
            countries?.let {
                movieRecyclerView.visibility = View.VISIBLE
                movieListAdapter.updateCountries(it) }
        })
        viewModel.movieLoadError.observe(this, Observer { isError ->
            errorTextView.visibility = if(isError == "") View.GONE else View.VISIBLE
        })
        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                progressBar.visibility = if(it) View.VISIBLE else View.GONE
                if(it) {
                    errorTextView.visibility = View.GONE
                    movieRecyclerView.visibility = View.GONE
                }
            }
        })
    }
}