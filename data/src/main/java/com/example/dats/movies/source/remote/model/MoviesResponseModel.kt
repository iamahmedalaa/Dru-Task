package com.example.dats.movies.source.remote.model



data class MoviesCatResponse(
    val genres: List<MovieCategory> = emptyList(),
)

data class MoviesResponseModel(
    val page: Int? = null,
    val totalPages: Int? = null,
    val results: List<MovieModel> = emptyList(),
    val totalResults: Int? = null
)