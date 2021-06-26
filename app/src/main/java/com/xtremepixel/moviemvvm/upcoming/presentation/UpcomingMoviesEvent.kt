package com.xtremepixel.moviemvvm.upcoming.presentation

sealed class UpcomingMoviesEvent {
    object RequestInitialMoviesList: UpcomingMoviesEvent()
    object RequestMoreMoviess: UpcomingMoviesEvent()
}