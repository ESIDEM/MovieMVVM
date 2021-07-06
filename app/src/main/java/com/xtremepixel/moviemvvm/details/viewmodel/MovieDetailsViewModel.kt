package com.xtremepixel.moviemvvm.details.viewmodel

import androidx.lifecycle.*
import com.xtremepixel.moviemvvm.common.data.utils.DispatchersProvider
import com.xtremepixel.moviemvvm.common.data.utils.createExceptionHandler
import com.xtremepixel.moviemvvm.common.domain.model.*
import com.xtremepixel.moviemvvm.common.presentation.Event
import com.xtremepixel.moviemvvm.common.presentation.model.MovieUI
import com.xtremepixel.moviemvvm.common.presentation.model.mapper.MovieUIMapper
import com.xtremepixel.moviemvvm.details.MovieDetailsState
import com.xtremepixel.moviemvvm.details.domain.GetMovieDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetails: GetMovieDetails,

    private val dispatchersProvider: DispatchersProvider,
):ViewModel() {
    private val movieUiMapper = MovieUIMapper()
        val state: LiveData<MovieDetailsState> get() = _state

        private val _state = MutableLiveData<MovieDetailsState>()
    private val _movie = MutableLiveData<MovieUI>()
    val movie:MutableLiveData<MovieUI> get()= _movie
    val loadingState = MutableLiveData<Boolean>()
    val error = MutableLiveData<Event<Throwable>>()


     fun loadMovieDetails(moviedId:Int) {
         loadingState.value = true
        val errorMessage = "Failed to fetch Movies"
        val exceptionHandler = viewModelScope.createExceptionHandler(errorMessage) { onFailure(it) }

        viewModelScope.launch(exceptionHandler) {
            val movieDetails = withContext(dispatchersProvider.io()) {

                getMovieDetails(moviedId)
            }
            _movie.value = movieUiMapper.mapToView(movieDetails)
            loadingState.value = false

        }
    }

    private fun onFailure(failure: Throwable) {
        when (failure) {
            is NetworkException,
            is NetworkUnavailableException -> {
                error.value = Event(failure)
                loadingState.value = false
            }
        }}

}