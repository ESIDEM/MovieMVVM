package com.xtremepixel.moviemvvm.movie_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.xtremepixel.moviemvvm.R
import com.xtremepixel.moviemvvm.api.MovieDbClient
import com.xtremepixel.moviemvvm.api.MovieDbInterface
import com.xtremepixel.moviemvvm.api.POSTER_BASE_URL
import com.xtremepixel.moviemvvm.databinding.ActivityMovieDetailsBinding
import com.xtremepixel.moviemvvm.models.MovieData
import com.xtremepixel.moviemvvm.repository.NetworkState
import java.text.NumberFormat
import java.util.*


class MovieDetails : AppCompatActivity() {
    private lateinit var  viewModel: MovieDetailsViewModel
    private lateinit var movieDetailsRepo: MovieDetailsRepo
    private lateinit var movieDetailsBinding: ActivityMovieDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieDetailsBinding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(movieDetailsBinding.root)
        val movie_id = 337404
        val movieDbInterface = MovieDbClient.getClient()
        movieDetailsRepo = MovieDetailsRepo(movieDbInterface)
        viewModel = getViewModel(movie_id)

//        viewModel.movieDetails.observe(this, Observer {
//            bindUI(it)
//        })

        viewModel.networkState.observe(this, Observer {
            movieDetailsBinding.progressBar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            movieDetailsBinding.errorText.visibility = if (it== NetworkState.ERROR) View.VISIBLE else View.GONE
        })

    }

    private fun bindUI(it: MovieData) {

        val formatCurency = NumberFormat.getCurrencyInstance(Locale.US)
        movieDetailsBinding.movieTitle.text = it.title
        movieDetailsBinding.movieDescription.text = it.tagline
        movieDetailsBinding.releaseDateValue.text = it.releaseDate
        movieDetailsBinding.budgetValue.text =formatCurency.format( it.budget)
        movieDetailsBinding.revenueValue.text = formatCurency.format(it.revenue)
        movieDetailsBinding.overviewValue.text = it.overview
        movieDetailsBinding.runtimeValue.text = it.runtime.toString() + "munites"
        val moviePosterUrl = POSTER_BASE_URL+it.posterPath

        Glide.with(this)
            .load(moviePosterUrl)
            .into(movieDetailsBinding.imageView)

    }

    private fun getViewModel(movie_id:Int):MovieDetailsViewModel{

        return  ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return  MovieDetailsViewModel(movieDetailsRepo,movie_id) as T
            }

        })[MovieDetailsViewModel::class.java]

    }

    private  fun iniViewModel(){
        viewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)
        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })

    }
}