package com.xtremepixel.moviemvvm.common.domain.repository

import com.xtremepixel.moviemvvm.common.domain.model.MovieDomain
import com.xtremepixel.moviemvvm.common.domain.model.PaginatedMovie
import io.reactivex.Flowable

interface MovieRepository {

    suspend fun requestPopularMovies(pageToLoad: Int): PaginatedMovie
}