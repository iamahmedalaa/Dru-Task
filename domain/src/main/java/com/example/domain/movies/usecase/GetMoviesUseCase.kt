package com.example.domain.movies.usecase

import com.example.domain.movies.repository.MoviesRepository
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    suspend fun loadMovies(categoryId: String?) = moviesRepository.loadMovies(categoryId)
}