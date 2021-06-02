package com.xtremepixel.moviemvvm.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.xtremepixel.moviemvvm.models.MovieData
import com.xtremepixel.moviemvvm.repository.NetworkState
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MovieDetailsViewModel(private val movieDetailsRepo: MovieDetailsRepo,movie_id:Int):ViewModel() {

    private  val compositeDisposable = CompositeDisposable()
    val movieDetails:LiveData<MovieData> by lazy {
        movieDetailsRepo.fetchMovieDetails(compositeDisposable,movie_id)
    }

    fun getData(movie_id: Int){
        movieDetailsRepo.fetchMovieDetails(compositeDisposable,movie_id)
    }
    val networkState:LiveData<NetworkState> by lazy {
        movieDetailsRepo.getMovieNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}