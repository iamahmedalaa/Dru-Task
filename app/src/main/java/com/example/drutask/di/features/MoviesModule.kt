package com.example.drutask.di.features


import com.example.dats.db.DruTaskDatabase
import com.example.dats.movies.repository.MoviesRepositoryImpl
import com.example.dats.movies.source.local.MoviesLocalDataSource
import com.example.dats.movies.source.local.RemoteKeysLocalDataSource
import com.example.dats.movies.source.remote.MoviesRemoteDataSource
import com.example.dats.movies.source.remote.api.MoviesApi
import com.example.domain.movies.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class MoviesModule {

    @Provides
    fun provideMoviesLocalDataSource(
        db: DruTaskDatabase
    ): MoviesLocalDataSource =
        MoviesLocalDataSource(db.moviesDao())

    @Provides
    fun provideRemoteKeysLocalDataSource(
        db: DruTaskDatabase
    ): RemoteKeysLocalDataSource =
        RemoteKeysLocalDataSource(db.remoteKeysDao())

    @Provides
    fun provideMoviesService(retrofit: Retrofit): MoviesApi {
        return retrofit.create(MoviesApi::class.java)
    }

    @Provides
    fun provideMoviesRemoteDataSource(
        moviesApi: MoviesApi,
        moviesLocalDataSource: MoviesLocalDataSource,
        remoteKeysLocalDataSource: RemoteKeysLocalDataSource,
    ): MoviesRemoteDataSource =
        MoviesRemoteDataSource(
            moviesApi,
            moviesLocalDataSource,
            remoteKeysLocalDataSource,
        )

    @Provides
    fun provideMoviesRepository(
        moviesLocalDataSource: MoviesLocalDataSource,
        moviesRemoteDataSource: MoviesRemoteDataSource
    ): MoviesRepository =
        MoviesRepositoryImpl(moviesLocalDataSource, moviesRemoteDataSource)

}