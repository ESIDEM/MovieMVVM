package com.xtremepixel.moviemvvm.upcoming.domain

import com.xtremepixel.moviemvvm.common.domain.model.NoMoreMoviesException
import com.xtremepixel.moviemvvm.common.domain.model.PaginatedMovie
import com.xtremepixel.moviemvvm.common.domain.repository.MovieRepository
import javax.inject.Inject

class GetUpcomingMovies @Inject constructor(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(
        pageToLoad: Int,
    ): PaginatedMovie {
        val response = movieRepository.requestUpcominingMovies(pageToLoad)

        if (response.movies.isEmpty()) {
            throw NoMoreMoviesException("No more Movies :(")
        }

        return response
    }
}