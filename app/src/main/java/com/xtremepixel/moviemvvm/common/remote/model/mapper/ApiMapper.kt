package com.xtremepixel.moviemvvm.common.remote.model.mapper

interface ApiMapper<E, D> {

    fun mapToDomain(apiEntity: E): D
}
