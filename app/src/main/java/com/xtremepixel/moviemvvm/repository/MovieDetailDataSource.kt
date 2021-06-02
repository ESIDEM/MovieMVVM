package com.xtremepixel.moviemvvm.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xtremepixel.moviemvvm.api.MovieDbInterface
import com.xtremepixel.moviemvvm.models.MovieData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.Exception

class MovieDetailDataSource(private val movieDbInterface: MovieDbInterface,
                            private val  compositeDisposable: CompositeDisposable) {

    private val _netWorkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
    get() = _netWorkState

    private val _downloadedMovieResponse = MutableLiveData<MovieData>()
    val downloadedMovieResponse:LiveData<MovieData>
    get() = _downloadedMovieResponse

    fun fetMovieDetails(movie_id:Int){

        _netWorkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(movieDbInterface.getMovieDetails(movie_id).subscribeOn(
                Schedulers.io()
            )
                .subscribe(
                    {
                        _downloadedMovieResponse.postValue(it)
                        _netWorkState.postValue(NetworkState.LOADED)
                    },
                    {
                        _netWorkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDetailsDataSource", it.message.toString())
                    }
                )
            )

        }
        catch (e:Exception){
            Log.e("MovieDetailsDataSource", e.message.toString())
        }

    }

}