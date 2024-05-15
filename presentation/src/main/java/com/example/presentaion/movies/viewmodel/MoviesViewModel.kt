package com.example.presentaion.movies.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.core.util.DispatcherProvider
import com.example.domain.movies.usecase.GetMoviesCategoriesUseCase
import com.example.domain.movies.usecase.GetMoviesUseCase
import com.example.presentaion.base.BaseViewModel
import com.example.presentaion.base.IGlobalState
import com.example.presentaion.movies.contract.MoviesContract.Event
import com.example.presentaion.movies.contract.MoviesContract.State
import com.example.presentaion.movies.contract.MoviesContract.Effect
import com.example.presentaion.movies.model.MovieUiModel
import com.example.presentaion.movies.model.mapper.mapToUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MoviesViewModel @Inject constructor(
    globalState: IGlobalState,
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getMoviesCategoriesUseCase: GetMoviesCategoriesUseCase,
    private val dispatchers: DispatcherProvider,
) : BaseViewModel<Event, State, Effect>(globalState, dispatchers) {

    private var isInitialized = false

    init {

        loadCategories()
    }

    fun loadMovies() {
        if (isInitialized) return
        loadMovies(categoryId = null)
        // Update isInitialized flag
        isInitialized = true
    }

    private fun loadMovies(categoryId: String? = null) {
        viewModelScope.launch {

            val flow = getMoviesUseCase.loadMovies(categoryId)
                .flowOn(dispatchers.io)
                .map { it.map { it.mapToUi() } }
                .cachedIn(viewModelScope)

            setState { copy(movies = flow) }
        }
    }

    private fun loadMoviesByCategory(categoryId: String? = null) {
        loadMovies(categoryId)
    }


    fun loadCategories() {
        viewModelScope.launch {
            getMoviesCategoriesUseCase.loadCategories()
                .flowOn(dispatchers.io)
                .map { it.map { it.mapToUi() } }
                .collectLatest {
                    setState { copy(categories = it) }
                }
        }
    }


    override fun setInitialState() = State()

    override fun handleEvents(event: Event) = when (event) {
        is Event.OnItemClick -> handleMovieItemClick(event.movieUiModel)
        is Event.OnCategoryItemClick -> loadMoviesByCategory(event.categoryUi.id.toString())
    }

    private fun handleMovieItemClick(movieUiModel: MovieUiModel) {
        movieUiModel.id ?: return

        // Then navigate to contractor profile
        setEffect { Effect.Navigation.ToMovieDetails(movieUiModel.id) }
    }

}