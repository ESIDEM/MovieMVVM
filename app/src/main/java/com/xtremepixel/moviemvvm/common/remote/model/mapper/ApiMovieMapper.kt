package com.xtremepixel.moviemvvm.common.remote.model.mapper

import com.xtremepixel.moviemvvm.common.domain.model.MovieDomain
import com.xtremepixel.moviemvvm.common.remote.model.Movie
import com.xtremepixel.moviemvvm.models.MovieDetail

class ApiMovieMapper():ApiMapper<Movie,MovieDomain> {
    override fun mapToDomain(apiEntity: Movie): MovieDomain {
        return MovieDomain(
            adult = apiEntity.adult,
            backdropPath = apiEntity.backdropPath,
            genreIds = apiEntity.genreIds,
            id = apiEntity.id,
            originalLanguage = apiEntity.originalLanguage,
            originalTitle = apiEntity.originalTitle,
            overview = apiEntity.overview,
            popularity = apiEntity.popularity,
            posterPath = apiEntity.posterPath,
            releaseDate = apiEntity.releaseDate,
            title = apiEntity.title,
            video = apiEntity.video,
            voteAverage = apiEntity.voteAverage,
            voteCount = apiEntity.voteCount

        )
    }

    fun mapMovieDetailToDomain(movieDetail: MovieDetail):MovieDomain{

        return MovieDomain(
            adult = movieDetail.adult,
            backdropPath = movieDetail.backdrop_path,
            genreIds = null,
            id = movieDetail.id,
            originalLanguage = movieDetail.original_language,
            originalTitle = movieDetail.original_title,
            overview = movieDetail.overview,
            popularity = movieDetail.popularity,
            posterPath = movieDetail.poster_path,
            releaseDate = movieDetail.release_date,
            title = movieDetail.title,
            video = movieDetail.video,
            voteAverage = movieDetail.vote_average,
            voteCount = movieDetail.vote_count

        )
    }
}