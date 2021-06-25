package com.xtremepixel.moviemvvm.common.remote.model


import com.google.gson.annotations.SerializedName
import com.xtremepixel.moviemvvm.common.remote.model.Movie

data class MovieRemoteResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)