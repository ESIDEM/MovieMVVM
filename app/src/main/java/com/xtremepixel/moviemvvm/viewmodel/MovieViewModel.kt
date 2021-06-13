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

    fun getMovies() {
        viewModelScope.launch {
            movieRepository.getMovies().collect {
                _movieList.value = it
            }
        }

    }
init {
    getMovies()
}
}