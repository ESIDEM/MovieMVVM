package com.xtremepixel.moviemvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xtremepixel.moviemvvm.api.MovieDBService
import com.xtremepixel.moviemvvm.models.Movie
import kotlinx.coroutines.*

class MovieViewModel():ViewModel() {

    val movieDBService = MovieDBService.getMovieDbInterface()
    var job:Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val movies = MutableLiveData<List<Movie>>()
    val movieLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    private fun onError(s: String) {
        movieLoadError.value = s
        loading.value = false
    }

    private fun fetchMovies() {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = movieDBService.getPopularMovies("205d1d516432851bf13809d2adf550bd")
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    movies.value = response.body()?.results
                    movieLoadError.value = null
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
        movieLoadError.value = ""
        loading.value = false
    }

    fun refresh() {
        fetchMovies()
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}