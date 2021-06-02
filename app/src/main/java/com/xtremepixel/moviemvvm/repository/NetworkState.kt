package com.xtremepixel.moviemvvm.repository

class NetworkState(val status: Status, val message:String) {

    companion object {

        val LOADED : NetworkState
        val LOADING : NetworkState
        val ERROR :NetworkState

        init {
            LOADED = NetworkState(Status.SUCCESS,"Success")
            LOADING = NetworkState(Status.RUNNING,"Running")
            ERROR = NetworkState(Status.FAILURE,"Something went wrong")
        }
    }
}

enum class Status{

    RUNNING,
    SUCCESS,
    FAILURE
}