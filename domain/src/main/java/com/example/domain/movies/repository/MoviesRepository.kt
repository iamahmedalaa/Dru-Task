package com.example.domain.movies.repository

import androidx.paging.PagingData
import com.example.domain.movies.model.MovieCategoryDomain
import com.example.domain.movies.model.MovieDomainModel
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun loadMovies(categoryId: String?): Flow<PagingData<MovieDomainModel>>

    suspend fun loadCategories() : Flow<List<MovieCategoryDomain>>
}