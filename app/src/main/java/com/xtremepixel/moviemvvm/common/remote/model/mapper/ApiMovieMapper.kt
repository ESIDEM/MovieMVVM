package com.xtremepixel.moviemvvm.common.remote.model.mapper

import com.xtremepixel.moviemvvm.common.domain.model.MovieDomain
import com.xtremepixel.moviemvvm.common.remote.model.Movie

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
}