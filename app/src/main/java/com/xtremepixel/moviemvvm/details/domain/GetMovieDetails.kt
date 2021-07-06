package com.xtremepixel.moviemvvm.details.domain

import com.xtremepixel.moviemvvm.common.domain.model.MovieDomain
import com.xtremepixel.moviemvvm.common.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetails @Inject constructor(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(
        movieId: Int,
    ): MovieDomain {
        val response = movieRepository.requestMovieDetails(movieId)
        return response
    }
}