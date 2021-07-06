package com.xtremepixel.moviemvvm.details

sealed class MovieDetailsEvent {
    object RequestMoviesDetails: MovieDetailsEvent()
}