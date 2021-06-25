package com.xtremepixel.moviemvvm.common.data

import com.xtremepixel.moviemvvm.common.domain.model.MovieDomain
import com.xtremepixel.moviemvvm.common.domain.model.NetworkException
import com.xtremepixel.moviemvvm.common.domain.model.PaginatedMovie
import com.xtremepixel.moviemvvm.common.domain.repository.MovieRepository
import com.xtremepixel.moviemvvm.common.remote.MovieDbInterface
import com.xtremepixel.moviemvvm.common.remote.model.mapper.ApiMovieMapper
import com.xtremepixel.moviemvvm.common.remote.model.mapper.ApiPaginationMapper
import io.reactivex.Flowable
import retrofit2.HttpException
import javax.inject.Inject

class RepositoryImpL @Inject constructor(
    private val api: MovieDbInterface,

    private val apiPaginationMapper: ApiPaginationMapper
): MovieRepository {
    private val apiAnimalMapper = ApiMovieMapper()
    override suspend fun requestPopularMovies(pageToLoad: Int): PaginatedMovie {
        try {
            val response = api.getPopularMovies(pageToLoad)

            return PaginatedMovie(
                response.results.map { apiAnimalMapper.mapToDomain(it) }.orEmpty(),
                apiPaginationMapper.mapToDomain(response)
            )
        } catch (exception: HttpException) {
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }
}