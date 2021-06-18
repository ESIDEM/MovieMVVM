package com.xtremepixel.moviemvvm.data

import com.xtremepixel.moviemvvm.models.MovieDetail
import com.xtremepixel.moviemvvm.models.MovieModel
import com.xtremepixel.moviemvvm.remote.MovieDbInterface
import retrofit2.Retrofit
import javax.inject.Inject

class MoviesRemoteDataStore @Inject constructor(private val retrofit: Retrofit) :BaseDataSource() {
        val movieServices = retrofit.create(MovieDbInterface::class.java)
    suspend fun getPopularMovies(page : Int) : Resource<MovieModel>{
        return  getResult {
        movieServices.getPopularMovies(page)
        }
    }


    suspend fun getUpcomingMovie(page: Int) : Resource<MovieModel>{
        return getResult {
            movieServices.getUpcomingMovies(page)
        }
    }

    suspend fun getMovieDetails(movie_id:String):Resource<MovieDetail>{
       return getResult {
           movieServices.getSingleMovie(movie_id)
       }
    }
}