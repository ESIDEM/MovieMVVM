package com.xtremepixel.moviemvvm.common.presentation.model

import com.xtremepixel.moviemvvm.format

const val PLACEHOLDER = "--"
data class MovieUI(
    val adult: Boolean?,
    val backdropPath: String?,
    val genreIds: List<Int>?,
    val id: Int?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    val voteAverage: Double?,
    val voteCount: Int?
)

fun MovieUI.displayReleaseDate(): String = this.releaseDate ?: PLACEHOLDER
fun MovieUI.displayVoteCount(): String = this.voteCount?.format() ?: PLACEHOLDER
fun MovieUI.displayVotePercentage(): String = "${this.voteAverage?.times(10) ?: PLACEHOLDER}%"