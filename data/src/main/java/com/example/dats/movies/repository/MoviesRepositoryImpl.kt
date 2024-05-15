package com.example.dats.movies.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.core.util.APIConst.Companion.PAGE_SIZE
import com.example.dats.movies.source.local.MoviesLocalDataSource
import com.example.dats.movies.source.remote.MoviesRemoteDataSource
import com.example.dats.movies.source.remote.model.mapper.mapToDomain
import com.example.domain.movies.model.MovieDomainModel
import com.example.domain.movies.repository.MoviesRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesLocalDataSource: MoviesLocalDataSource,
    private val moviesRemoteDataSource: MoviesRemoteDataSource
) : MoviesRepository {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun loadMovies(categoryId: String?): Flow<PagingData<MovieDomainModel>> {
        moviesRemoteDataSource.catId = categoryId
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = PAGE_SIZE,
                prefetchDistance = 10,
                initialLoadSize = PAGE_SIZE,
            ),
            pagingSourceFactory = {
                moviesLocalDataSource.getMovies(categoryId)
            },
            remoteMediator = moviesRemoteDataSource
        ).flow.map {
            it.map { it.mapToDomain() }
        }
    }


    override suspend fun loadCategories() =
        moviesRemoteDataSource.loadAndCacheCategories().map { it.map { it.mapToDomain() } }

}