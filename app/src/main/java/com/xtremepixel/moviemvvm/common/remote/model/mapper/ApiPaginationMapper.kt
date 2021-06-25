package com.xtremepixel.moviemvvm.common.remote.model.mapper

import com.xtremepixel.moviemvvm.common.domain.model.Pagination
import com.xtremepixel.moviemvvm.common.remote.model.MovieRemoteResponse
import javax.inject.Inject

class ApiPaginationMapper @Inject constructor(): ApiMapper<MovieRemoteResponse?, Pagination> {

    override fun mapToDomain(apiEntity: MovieRemoteResponse?): Pagination {
        return Pagination(
            currentPage = apiEntity?.page ?: 0,
            totalPages = apiEntity?.totalPages ?: 0
        )
    }
}