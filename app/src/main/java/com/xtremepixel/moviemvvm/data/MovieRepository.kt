package com.xtremepixel.moviemvvm.data

import com.xtremepixel.moviemvvm.models.MovieDetail
import com.xtremepixel.moviemvvm.models.MovieModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepository @Inject constructor(private val moviesRemoteDataStore: MoviesRemoteDataStore) {

    suspend fun getPopularMovies(page:Int): Flow<Resource<MovieModel>?>{

        return flow {
            emit(Resource.loading())
            val result = moviesRemoteDataStore.getPopularMovies(page)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUpcomingMovies(page:Int): Flow<Resource<MovieModel>?>{

        return flow {
            emit(Resource.loading())
            val result = moviesRemoteDataStore.getUpcomingMovie(page)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMoviesDetails(movie_id:String): Flow<Resource<MovieDetail>?>{

        return flow {
            emit(Resource.loading())
            val result = moviesRemoteDataStore.getMovieDetails(movie_id)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

}