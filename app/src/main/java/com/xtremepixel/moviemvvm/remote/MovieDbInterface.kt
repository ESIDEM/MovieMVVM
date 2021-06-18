package com.xtremepixel.moviemvvm.remote

import com.xtremepixel.moviemvvm.models.MovieDetail
import com.xtremepixel.moviemvvm.models.MovieModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieDbInterface {
    // https://api.themoviedb.org/3/movie/337404?api_key=205d1d516432851bf13809d2adf550bd
    //https://api.themoviedb.org/3/movie/popular?api_key=205d1d516432851bf13809d2adf550bd&page=1
    //https://api.themoviedb.org/3/

//    @GET("movie/{movie_id}")
//    fun getMovieDetails(@Path("movie_id")id:Int): Single<MovieData>
//

@GET("movie/popular")
suspend fun getPopularMovies(@Query("page") page: Int):Response<MovieModel>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Int): Response<MovieModel>

    @GET("movie/{id}")
    fun getSingleMovie(@Path("id") id: String): Response<MovieDetail>
}