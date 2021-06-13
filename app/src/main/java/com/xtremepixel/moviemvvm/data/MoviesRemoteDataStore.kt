package com.xtremepixel.moviemvvm.data

import com.xtremepixel.moviemvvm.models.MovieModel
import com.xtremepixel.moviemvvm.remote.MovieDbInterface
import retrofit2.Retrofit
import javax.inject.Inject

class MoviesRemoteDataStore @Inject constructor(private val retrofit: Retrofit) :BaseDataSource() {

    suspend fun getMoviess() : Resource<MovieModel>{
        val movieServices = retrofit.create(MovieDbInterface::class.java)
        return  getResult {
        movieServices.getPopularMovies()
        }
    }


}