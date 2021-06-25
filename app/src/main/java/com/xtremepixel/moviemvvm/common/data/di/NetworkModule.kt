package com.xtremepixel.moviemvvm.common.data.di

import com.xtremepixel.moviemvvm.ApiParameters
import com.xtremepixel.moviemvvm.Config
import com.xtremepixel.moviemvvm.common.presentation.model.mapper.MovieUIMapper
import com.xtremepixel.moviemvvm.common.remote.MovieDbInterface
import com.xtremepixel.moviemvvm.common.remote.interceptors.AuthInterceptor
import com.xtremepixel.moviemvvm.common.remote.interceptors.NetworkStatusInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor;
    }
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor,
                            networkStatusInterceptor: NetworkStatusInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(networkStatusInterceptor)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AuthInterceptor(ApiParameters.API_KEY))
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): MovieDbInterface {
        return retrofit
            .create(MovieDbInterface::class.java)
    }


}