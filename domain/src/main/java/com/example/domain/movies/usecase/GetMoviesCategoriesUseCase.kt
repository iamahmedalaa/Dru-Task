package com.example.domain.movies.usecase

import com.example.domain.movies.model.MovieCategoryDomain
import com.example.domain.movies.repository.MoviesRepository
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMoviesCategoriesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    suspend fun loadCategories() = moviesRepository.loadCategories().flatMapLatest {
        val categoryAll = MovieCategoryDomain("All", null)
        val categoryMutable = it.toMutableList()
        categoryMutable.add(0, categoryAll)
        flowOf(categoryMutable)
    }
}