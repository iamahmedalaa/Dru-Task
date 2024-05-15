package com.example.dats.moviedetails.repository

import android.annotation.SuppressLint
import com.example.dats.moviedetails.source.local.MovieDetailsLocalDataSource
import com.example.dats.moviedetails.source.remote.api.MovieDetailsApi
import com.example.dats.moviedetails.source.local.model.mapper.mapToDomain
import com.example.dats.moviedetails.source.local.model.mapper.mapToLocal
import com.example.domain.moviedetails.repository.MovieDetailsRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val movieDetailsApi: MovieDetailsApi,
    private val movieDetailsLocalDataSource: MovieDetailsLocalDataSource
) : MovieDetailsRepository {


    @SuppressLint("SuspiciousIndentation")
    override suspend fun loadMovieDetails(movieId: Long) = flow {
        val localMovie = movieDetailsLocalDataSource.getMovie(movieId)

        localMovie?.let {
            emit(it.mapToDomain())
        } ?: run {
          val remoteToLocal = movieDetailsApi
                .fetchMovieDetails(movieId).mapToLocal()
            movieDetailsLocalDataSource
                .insertMovie(remoteToLocal)
            emit(remoteToLocal.mapToDomain())
        }
    }

}