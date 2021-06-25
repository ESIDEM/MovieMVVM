package com.xtremepixel.moviemvvm.popular.presentation

import com.xtremepixel.moviemvvm.common.presentation.Event
import com.xtremepixel.moviemvvm.common.presentation.model.MovieUI

data class PopularMovieState(
    val loading: Boolean = true,
    val movies: List<MovieUI> = emptyList(),
    val noMoreMovies: Boolean = false,
    val failure: Event<Throwable>? = null
)