package com.xtremepixel.moviemvvm.viewmodel

import androidx.lifecycle.*
import com.xtremepixel.moviemvvm.data.MovieRepository
import com.xtremepixel.moviemvvm.data.Resource
import com.xtremepixel.moviemvvm.models.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository):ViewModel() {

    private val _movieList = MutableLiveData<Resource<MovieModel>>()
    val movieData = _movieList
    private var currentPage = 1
    private var lastPage = 100

    fun getMovies() {
        viewModelScope.launch {
            movieRepository.getPopularMovies(currentPage).collect {
                _movieList.value = it
               // lastPage = it!!.data!!.totalPages
            }
        }

    }


    fun fetchNextPopularMovies() {
        currentPage++
        getMovies()
    }

    fun refreshPopularMovies() {
        currentPage = 1
        getMovies()
    }

    fun isFirstPage(): Boolean {
        return currentPage == 1
    }

    fun isLastPage(): Boolean {
        return currentPage == lastPage
    }

}