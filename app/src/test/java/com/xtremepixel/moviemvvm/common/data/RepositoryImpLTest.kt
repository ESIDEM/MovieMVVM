package com.xtremepixel.moviemvvm.common.data

import com.xtremepixel.moviemvvm.common.domain.model.PaginatedMovie
import com.xtremepixel.moviemvvm.common.remote.MovieDbInterface
import com.xtremepixel.moviemvvm.common.remote.model.MovieRemoteResponse
import com.xtremepixel.moviemvvm.common.remote.model.mapper.ApiPaginationMapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class RepositoryImpLTest{

    @Test
    fun `a Paginated movie item is returned`() = runBlocking {
        val api = mockk<MovieDbInterface>()
        val mapper = mockk<ApiPaginationMapper>()
        val pagedMovie = mockk<PaginatedMovie>()
        val moviesRespomse = mockk<MovieRemoteResponse>()


        coEvery { api.getPopularMovies(any()) } returns
                moviesRespomse

        coVerify { api.getPopularMovies(1) }

        val provider = RepositoryImpL(api,mapper)

        coEvery { provider.requestPopularMovies(1) } returns
                pagedMovie
        val movies = provider.requestPopularMovies(1)
        assertEquals(pagedMovie,movies)
    }
}