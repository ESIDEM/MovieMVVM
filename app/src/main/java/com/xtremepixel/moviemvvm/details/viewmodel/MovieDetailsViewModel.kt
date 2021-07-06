package com.xtremepixel.moviemvvm.details.viewmodel

import androidx.lifecycle.*
import com.xtremepixel.moviemvvm.common.data.utils.DispatchersProvider
import com.xtremepixel.moviemvvm.common.data.utils.createExceptionHandler
import com.xtremepixel.moviemvvm.common.domain.model.*
import com.xtremepixel.moviemvvm.common.presentation.Event
import com.xtremepixel.moviemvvm.common.presentation.model.mapper.MovieUIMapper
import com.xtremepixel.moviemvvm.details.MovieDetailsEvent
import com.xtremepixel.moviemvvm.details.MovieDetailsState
import com.xtremepixel.moviemvvm.details.domain.GetMovieDetails
import com.xtremepixel.moviemvvm.popular.domain.GetPopularMovies
import com.xtremepixel.moviemvvm.popular.presentation.PopularMovieState
import com.xtremepixel.moviemvvm.popular.presentation.PopularMoviesEvent
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
    companion object {
        const val UI_PAGE_SIZE = Pagination.DEFAULT_PAGE_SIZE
    }

        val state: LiveData<MovieDetailsState> get() = _state

        private val _state = MutableLiveData<MovieDetailsState>()


    private fun onMovieDetailsObtain(movieDomain: MovieDomain) {

        val movies =  movieUiMapper.mapToView(movieDomain)


        _state.value = state.value!!.copy( loading = false, moviesDetails = movies)
    }


    private fun loadMovieDetails(moviedId:Int) {

        val errorMessage = "Failed to fetch Movies"
        val exceptionHandler = viewModelScope.createExceptionHandler(errorMessage) { onFailure(it) }

        viewModelScope.launch(exceptionHandler) {
            val movieDetails = withContext(dispatchersProvider.io()) {

                getMovieDetails(moviedId)
            }

            onMovieDetailsObtain(movieDetails)

        }
    }

    private fun onFailure(failure: Throwable) {
        when (failure) {
            is NetworkException,
            is NetworkUnavailableException -> {
                _state.value = state.value!!.copy(
                    loading = false,
                    failure = Event(failure)
                )
            }
        }}

}