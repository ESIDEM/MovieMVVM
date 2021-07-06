package com.xtremepixel.moviemvvm.details

import com.xtremepixel.moviemvvm.common.presentation.Event
import com.xtremepixel.moviemvvm.common.presentation.model.MovieUI

data class MovieDetailsState(
    val failure: Event<Throwable>? = null
)