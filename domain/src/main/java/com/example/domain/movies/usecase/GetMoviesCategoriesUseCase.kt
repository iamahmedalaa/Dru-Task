package com.example.domain.movies.usecase

import com.example.domain.movies.repository.MoviesRepository
import javax.inject.Inject

class GetMoviesCategoriesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    suspend fun loadCategories() = moviesRepository.loadCategories()
}