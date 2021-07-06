package com.xtremepixel.moviemvvm.common.remote

import com.xtremepixel.moviemvvm.ApiParameters
import com.xtremepixel.moviemvvm.Config
import com.xtremepixel.moviemvvm.models.MovieDetail
import com.xtremepixel.moviemvvm.common.remote.model.MovieRemoteResponse
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

@GET(Config.POPULAR_MOVIE_ENDPOINT)
suspend fun getPopularMovies(@Query(ApiParameters.PAGE) page: Int):MovieRemoteResponse

    @GET(Config.UPCOMING_MOVIE_ENDPOINT)
    suspend fun getUpcomingMovies(@Query(ApiParameters.PAGE) page: Int): MovieRemoteResponse

    @GET(Config.MOVIE_DETAIL_ENDPOINT)
    suspend fun getSingleMovie(@Path(ApiParameters.ID) id: Int): MovieDetail
}