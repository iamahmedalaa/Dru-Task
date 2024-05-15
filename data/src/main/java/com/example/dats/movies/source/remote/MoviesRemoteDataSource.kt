package com.example.dats.movies.source.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.dats.movies.source.local.MoviesLocalDataSource
import com.example.dats.movies.source.local.RemoteKeysLocalDataSource
import com.example.dats.movies.source.remote.api.MoviesApi
import com.example.dats.movies.source.remote.model.MovieModel
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteDataSource(
    private val moviesApi: MoviesApi,
    private val moviesLocalDataSource: MoviesLocalDataSource,
    private val remoteKeysLocalDataSource: RemoteKeysLocalDataSource,
) : RemoteMediator<Int, MovieModel>() {

    var catId: String? = null
    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (remoteKeysLocalDataSource.getCreationTime()
                ?: 0) < cacheTimeout
        ) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieModel>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys =
                    remoteKeysLocalDataSource.getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val remoteKeys = remoteKeysLocalDataSource.getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                prevKey ?: return MediatorResult
                    .Success(endOfPaginationReached = remoteKeys != null)

            }

            LoadType.APPEND -> {
                val remoteKeys = remoteKeysLocalDataSource.getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                nextKey ?: return MediatorResult
                    .Success(endOfPaginationReached = remoteKeys != null)
            }
        }
        return if (catId.isNullOrEmpty()) {
            loadAndCache(loadType, page)
        } else {
            MediatorResult.Success(endOfPaginationReached = true)
        }
    }

    private suspend fun loadAndCache(loadType: LoadType, page: Int): MediatorResult {
        return try {
            val apiResponse = moviesApi.fetchMoviesList(page = page)

            val movies = apiResponse.results
            val endOfPaginationReached = movies.isEmpty()

            if (loadType == LoadType.REFRESH) {
                remoteKeysLocalDataSource.clearRemoteKeys()
                moviesLocalDataSource.deleteAllMovies()
            }
            val remoteKeys = remoteKeysLocalDataSource.buildRemoteKey(page, movies)

            remoteKeysLocalDataSource.insertAll(remoteKeys)
            moviesLocalDataSource.insertMovies(movies.onEachIndexed { _, movie ->
                movie.page = page
            })

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: IOException) {
            MediatorResult.Error(error)
        } catch (error: HttpException) {
            MediatorResult.Error(error)
        }
    }

    suspend fun loadAndCacheCategories() = flow {
        val localCat = moviesLocalDataSource.getMoviesCategories()

        if (localCat.isNullOrEmpty()) {
            val apiResponse = moviesApi.fetchMoviesCategories()
            val categories = apiResponse.genres
            moviesLocalDataSource.insertCategories(categories)
            emit(categories)
        } else {
            emit(localCat)
        }
    }
}