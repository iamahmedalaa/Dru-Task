package com.example.dats.movies.source.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dats.movies.source.remote.model.MovieCategory
import com.example.dats.movies.source.remote.model.MovieModel

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieModel>)

    @Query("select * from MovieModel Order By page")
    fun getMovies(): PagingSource<Int, MovieModel>

    @Query("select * from MovieModel WHERE genre_ids like '%' || REPLACE(:catId, ',', '%') || '%'")
    fun getMoviesByCatId(catId: String): PagingSource<Int, MovieModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(movies: List<MovieCategory>)

    @Query("select * from MovieCategory")
    fun getMoviesCategories(): List<MovieCategory>

    @Query("delete from MovieModel")
    suspend fun deleteAllMovies()

}