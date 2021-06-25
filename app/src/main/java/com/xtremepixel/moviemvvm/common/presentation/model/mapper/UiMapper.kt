package com.xtremepixel.moviemvvm.common.presentation.model.mapper

interface UiMapper<E, V> {

    fun mapToView(input: E): V
}