package com.xtremepixel.moviemvvm.remote

import com.xtremepixel.moviemvvm.models.MovieModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Single


interface MovieDbInterface {
    // https://api.themoviedb.org/3/movie/337404?api_key=205d1d516432851bf13809d2adf550bd
    //https://api.themoviedb.org/3/movie/popular?api_key=205d1d516432851bf13809d2adf550bd&page=1
    //https://api.themoviedb.org/3/

//    @GET("movie/{movie_id}")
//    fun getMovieDetails(@Path("movie_id")id:Int): Single<MovieData>
//

@GET("movie/popular")
suspend fun getPopularMovies():Response<MovieModel>
}