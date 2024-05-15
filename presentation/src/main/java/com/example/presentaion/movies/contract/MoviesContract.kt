package com.example.presentaion.movies.contract

import androidx.paging.PagingData
import com.example.presentaion.base.ViewEvent
import com.example.presentaion.base.ViewSideEffect
import com.example.presentaion.base.ViewState
import com.example.presentaion.movies.model.MovieCategoryUi
import com.example.presentaion.movies.model.MovieUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MoviesContract {

    data class State(
        val movies: Flow<PagingData<MovieUiModel>> = flow { PagingData.empty<MovieUiModel>() },
        val categories: List<MovieCategoryUi> = listOf(),
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnItemClick(val movieUiModel: MovieUiModel) : Event()
        data class OnCategoryItemClick(val categoryUi: MovieCategoryUi) : Event()

    }

    object Navigation {
        object Routes {
            const val MY_HOME = "myHome"
        }
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class ToMovieDetails(val movieId: Long) : Effect()
        }
    }

}