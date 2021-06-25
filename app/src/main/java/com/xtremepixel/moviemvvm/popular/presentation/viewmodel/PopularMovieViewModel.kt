package com.xtremepixel.moviemvvm.popular.presentation.viewmodel

import androidx.lifecycle.*
import com.xtremepixel.moviemvvm.common.data.utils.DispatchersProvider
import com.xtremepixel.moviemvvm.common.data.utils.createExceptionHandler
import com.xtremepixel.moviemvvm.common.domain.model.*
import com.xtremepixel.moviemvvm.common.presentation.Event
import com.xtremepixel.moviemvvm.common.presentation.model.mapper.MovieUIMapper
import com.xtremepixel.moviemvvm.popular.domain.GetPopularMovies
import com.xtremepixel.moviemvvm.popular.presentation.PopularMovieState
import com.xtremepixel.moviemvvm.popular.presentation.PopularMoviesEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PopularMovieViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies,

    private val dispatchersProvider: DispatchersProvider,
):ViewModel() {
    private val movieUiMapper = MovieUIMapper()
    companion object {
        const val UI_PAGE_SIZE = Pagination.DEFAULT_PAGE_SIZE
    }

        val state: LiveData<PopularMovieState> get() = _state
        var isLoadingMoreMovie: Boolean = false
        var isLastPage = false

        private val _state = MutableLiveData<PopularMovieState>()
        private var currentPage = 0

        init {
            _state.value = PopularMovieState()
            loadMovies()
        }

        fun onEvent(event: PopularMoviesEvent) {
            when(event) {
                is PopularMoviesEvent.RequestInitialMoviesList -> loadMovies()
                is PopularMoviesEvent.RequestMoreMoviess -> loadNextMoviePage()
            }
        }

    private fun onNewMoviesList(items: List<MovieDomain>) {

        val movies = items.map { movieUiMapper.mapToView(it) }

        // This ensures that new items are added below the already existing ones, thus avoiding
        // repositioning of items that are already visible, as it can provide for a confusing UX. A
        // nice alternative to this would be to add an "updatedAt" field to the Room entities, so
        // that we could actually order them by something that we completely control.
        val currentList = state.value!!.movies
        val newMovies = movies.subtract(currentList)
        val updatedList = currentList + newMovies

        _state.value = state.value!!.copy( loading = false, movies = updatedList)
    }

    private fun loadMovies() {
        if (state.value!!.movies.isEmpty()) {
            loadNextMoviePage()
        }
    }
    private fun loadNextMoviePage() {
        isLoadingMoreMovie = true

        val errorMessage = "Failed to fetch Movies"
        val exceptionHandler = viewModelScope.createExceptionHandler(errorMessage) { onFailure(it) }

        viewModelScope.launch(exceptionHandler) {
            val pagination = withContext(dispatchersProvider.io()) {

                getPopularMovies(++currentPage)
            }

            onPaginationInfoObtained(pagination)
            isLoadingMoreMovie = false
        }
    }

    private fun onPaginationInfoObtained(pagination: PaginatedMovie) {
        onNewMoviesList(pagination.movies)
        currentPage = pagination.pagination.currentPage
        isLastPage = !pagination.pagination.canLoadMore
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
            is NoMoreMoviesException -> {
                _state.value = state.value!!.copy(
                    noMoreMovies = true,
                    failure = Event(failure)
                )
            }
        }
    }

}