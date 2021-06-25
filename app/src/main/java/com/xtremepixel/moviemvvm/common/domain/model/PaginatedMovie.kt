package com.xtremepixel.moviemvvm.common.domain.model

data class PaginatedMovie(
    val movies : List<MovieDomain>,
    val pagination: Pagination
)