package com.xtremepixel.moviemvvm.common.presentation.model.mapper

import com.xtremepixel.moviemvvm.common.domain.model.MovieDomain
import com.xtremepixel.moviemvvm.common.presentation.model.MovieUI
import javax.inject.Inject

class MovieUIMapper @Inject constructor():UiMapper<MovieDomain,MovieUI> {
    override fun mapToView(input: MovieDomain): MovieUI {
        return MovieUI(
            adult = input.adult,
            backdropPath = input.backdropPath,
            genreIds = input.genreIds,
            id = input.id,
            originalTitle = input.originalTitle,
            originalLanguage = input.originalLanguage,
            overview = input.overview,
            popularity = input.popularity,
            posterPath = input.posterPath,
            releaseDate = input.releaseDate,
            title = input.title,
            video = input.video,
            voteCount = input.voteCount,
            voteAverage = input.voteAverage
        )
    }
}