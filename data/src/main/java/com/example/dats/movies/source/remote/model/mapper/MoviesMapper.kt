package com.example.dats.movies.source.remote.model.mapper

import com.example.dats.movies.source.remote.model.MovieCategory
import com.example.dats.movies.source.remote.model.MovieModel
import com.example.domain.movies.model.MovieCategoryDomain
import com.example.domain.movies.model.MovieDomainModel


fun MovieModel.mapToDomain(): MovieDomainModel {
    return MovieDomainModel(
        overview = overview,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        video = video,
        title = title,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        id = id,
        adult = adult,
        voteCount = voteCount,
        genres = genre_ids,
    )
}

fun MovieCategory.mapToDomain(): MovieCategoryDomain {
    return MovieCategoryDomain(
        id = id,
        name = name,
    )
}


fun List<MovieModel>.mapToDomain(): List<MovieDomainModel> {
    return this.map {
        it.mapToDomain()
    }
}