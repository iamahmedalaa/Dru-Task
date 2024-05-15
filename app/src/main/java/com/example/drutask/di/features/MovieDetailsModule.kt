package com.example.drutask.di.features

import com.example.dats.moviedetails.repository.MovieDetailsRepositoryImpl
import com.example.dats.moviedetails.source.local.MovieDetailsLocalDataSource
import com.example.dats.moviedetails.source.local.dao.MovieDetailsDao
import com.example.dats.moviedetails.source.remote.api.MovieDetailsApi
import com.example.domain.moviedetails.repository.MovieDetailsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class MovieDetailsModule {

    @Provides
    fun provideMovieDetailsApiService(retrofit: Retrofit): MovieDetailsApi {
        return retrofit.create(MovieDetailsApi::class.java)
    }

    @Provides
    fun provideMovieDetailsLocalDataSource(
        movieDetailsDao: MovieDetailsDao
    ): MovieDetailsLocalDataSource {
        return MovieDetailsLocalDataSource(movieDetailsDao)
    }

    @Provides
    fun provideMoviesRepository(
        movieDetailsApi: MovieDetailsApi,
        movieDetailsLocalDataSource: MovieDetailsLocalDataSource
    ): MovieDetailsRepository =
        MovieDetailsRepositoryImpl(movieDetailsApi,movieDetailsLocalDataSource)
}