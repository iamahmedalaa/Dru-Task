package com.example.presentaion.moviedetails.contract

import com.example.presentaion.base.ViewEvent
import com.example.presentaion.base.ViewSideEffect
import com.example.presentaion.base.ViewState
import com.example.presentaion.moviedetails.model.MovieDetailsUiModel

class MovieDetailsContract {

    data class State(
        val movie: MovieDetailsUiModel? = null
    ) : ViewState

    sealed class Event : ViewEvent {
        object BackClick : Event()

    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object GoBack : Navigation()
        }
    }
}