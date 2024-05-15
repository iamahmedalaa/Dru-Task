package com.example.dats.moviedetails.source.local.model.mapper

import com.example.dats.moviedetails.source.local.model.MovieDetailsLocal
import com.example.dats.moviedetails.source.remote.model.GenresItem
import com.example.dats.moviedetails.source.remote.model.MovieDetailsResponse
import com.example.domain.moviedetails.model.MovieDetailsDomain

fun MovieDetailsResponse.mapToLocal(): MovieDetailsLocal {
    return MovieDetailsLocal(
        title = title,
        backdropPath = backdropPath,
        genres = genres.mapToLocal(),
        id = id,
        overview = overview,
        voteAverage = voteAverage?.round(),
        originalTitle = originalTitle,
        runtime = runtime,
        posterPath = posterPath,
        releaseDate = releaseDate,
        tagline = tagline,
    )
}

fun Float.round(decimals: Int = 1): Float = "%.${decimals}f".format(this).toFloat()

fun List<GenresItem>?.mapToLocal(): List<String> {
    return this?.filter { it.name != null }?.map { it.name?:"" }?: emptyList()

}

fun MovieDetailsLocal.mapToDomain(): MovieDetailsDomain {
    return MovieDetailsDomain(
        title = title,
        backdropPath = backdropPath,
        genres = genres.mapToDomain(),
        id = id,
        overview = overview,
        voteAverage = voteAverage,
        originalTitle = originalTitle,
        runtime = runtime,
        posterPath = posterPath,
        releaseDate = releaseDate,
        tagline = tagline,
    )
}

fun List<String>?.mapToDomain(): List<String> {
    return this?.map {it}?: emptyList()

}