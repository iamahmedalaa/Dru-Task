package com.example.presentaion.movies.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieUiModel(
    val overview: String? = null,
    val originalLanguage: String? = null,
    val originalTitle: String? = null,
    val video: Boolean? = null,
    val title: String? = null,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val releaseDate: String? = null,
    val voteAverage: Long? = null,
    val id: Long? = null,
    val adult: Boolean? = null,
    val voteCount: Int? = null,
    var genres:List<String> = emptyList()
) : Parcelable


data class MovieCategoryUi(
    val name: String? = null,
    val id: Long,
)