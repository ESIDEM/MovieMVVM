package com.xtremepixel.moviemvvm.data

import com.xtremepixel.moviemvvm.models.MovieModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepository @Inject constructor(private val moviesRemoteDataStore: MoviesRemoteDataStore) {

    suspend fun getMovies(): Flow<Resource<MovieModel>?>{

        return flow {
            emit(Resource.loading())
            val result = moviesRemoteDataStore.getMoviess()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

}