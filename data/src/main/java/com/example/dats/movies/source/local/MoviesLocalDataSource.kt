package com.example.dats.movies.source.local

import androidx.paging.PagingSource
import com.example.dats.movies.source.local.dao.MoviesDao
import com.example.dats.movies.source.remote.model.MovieCategory
import com.example.dats.movies.source.remote.model.MovieModel
import javax.inject.Inject

class MoviesLocalDataSource @Inject constructor(private val moviesDao: MoviesDao) {


    fun getMovies(categoryId: String?): PagingSource<Int, MovieModel> {
        return if (categoryId.isNullOrEmpty()) {
            moviesDao.getMovies()
        } else
            moviesDao.getMoviesByCatId(categoryId)
    }

    suspend fun insertMovies(movies: List<MovieModel>) {
        moviesDao.insertMovies(movies)
    }

    suspend fun deleteAllMovies() {
        moviesDao.deleteAllMovies()
    }

    suspend fun insertCategories(categories: List<MovieCategory>) {
        moviesDao.insertCategories(categories)
    }

    fun getMoviesCategories(): List<MovieCategory>? {
        return moviesDao.getMoviesCategories()
    }

}