package com.xtremepixel.moviemvvm.common.di

import com.xtremepixel.moviemvvm.common.data.RepositoryImpL
import com.xtremepixel.moviemvvm.common.data.utils.CoroutineDispatchersProvider
import com.xtremepixel.moviemvvm.common.data.utils.DispatchersProvider
import com.xtremepixel.moviemvvm.common.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindMovieRepo(repository: RepositoryImpL):MovieRepository

    @Binds
    abstract fun bindDispatchersProvider(dispatchersProvider: CoroutineDispatchersProvider):
            DispatchersProvider
}