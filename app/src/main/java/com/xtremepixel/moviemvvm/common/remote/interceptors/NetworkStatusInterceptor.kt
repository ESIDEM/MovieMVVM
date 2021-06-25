package com.xtremepixel.moviemvvm.common.remote.interceptors

import com.xtremepixel.moviemvvm.common.domain.model.NetworkUnavailableException
import com.xtremepixel.moviemvvm.common.remote.ConnectionManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkStatusInterceptor @Inject constructor(private val connectionManager: ConnectionManager) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (connectionManager.isConnected()) {
            chain.proceed(chain.request())
        } else {
            throw NetworkUnavailableException()
        }
    }
}
