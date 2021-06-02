package com.xtremepixel.moviemvvm.movie_details

import androidx.lifecycle.LiveData
import com.xtremepixel.moviemvvm.api.MovieDbInterface
import com.xtremepixel.moviemvvm.models.MovieData
import com.xtremepixel.moviemvvm.repository.MovieDetailDataSource
import com.xtremepixel.moviemvvm.repository.NetworkState
import io.reactivex.rxjava3.disposables.CompositeDisposable

// You can catch the data here

class MovieDetailsRepo(private  val movieDbInterface: MovieDbInterface) {

    lateinit var  movieDetailsDataSource: MovieDetailDataSource

    fun fetchMovieDetails(compositeDisposable: CompositeDisposable,movie_id:Int):LiveData<MovieData>{

        movieDetailsDataSource = MovieDetailDataSource(movieDbInterface,compositeDisposable)
        movieDetailsDataSource.fetMovieDetails(movie_id)

        return  movieDetailsDataSource.downloadedMovieResponse

    }

    fun getMovieNetworkState():LiveData<NetworkState>{
        return movieDetailsDataSource.networkState
    }
}