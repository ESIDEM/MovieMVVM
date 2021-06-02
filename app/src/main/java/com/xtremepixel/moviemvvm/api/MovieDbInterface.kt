package com.xtremepixel.moviemvvm.api

import com.xtremepixel.moviemvvm.models.MovieData
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path


interface MovieDbInterface {
    // https://api.themoviedb.org/3/movie/337404?api_key=205d1d516432851bf13809d2adf550bd
    //https://api.themoviedb.org/3/movie/popular?api_key=205d1d516432851bf13809d2adf550bd&page=1
    //https://api.themoviedb.org/3/

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id")id:Int): Single<MovieData>
}