package com.xtremepixel.moviemvvm.popular.presentation

sealed class PopularMoviesEvent {
    object RequestInitialMoviesList: PopularMoviesEvent()
    object RequestMoreMoviess: PopularMoviesEvent()
}