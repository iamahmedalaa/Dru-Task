package com.example.dats.movies.source.remote.api

import com.example.dats.movies.source.remote.model.MoviesCatResponse
import com.example.dats.movies.source.remote.model.MoviesResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("/3/discover/movie")
    suspend fun fetchMoviesList(@Query("page") page: Int): MoviesResponseModel

    @GET("/3/genre/movie/list")
    suspend fun fetchMoviesCategories(): MoviesCatResponse
}