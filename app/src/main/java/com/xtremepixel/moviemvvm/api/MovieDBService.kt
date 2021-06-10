package com.xtremepixel.moviemvvm.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieDBService {

    companion object{
        private const val BASE_URL = "https://api.themoviedb.org/3/"

         fun getMovieDbInterface():MovieDbInterface{

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieDbInterface::class.java)
        }
    }
}