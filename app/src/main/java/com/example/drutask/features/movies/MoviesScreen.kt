package com.example.drutask.features.movies

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.drutask.features.destinations.MovieDetailsScreenDestination
import com.example.presentaion.base.SIDE_EFFECTS_KEY
import com.example.presentaion.movies.contract.MoviesContract
import com.example.presentaion.movies.viewmodel.MoviesViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.onEach


@RootNavGraph(start = true)
@Destination
@Composable
fun MoviesScreen(navigator: DestinationsNavigator,
                 viewModel: MoviesViewModel = hiltViewModel()
) {

    // Handle side effects
    LaunchedEffect(SIDE_EFFECTS_KEY) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is MoviesContract.Effect.Navigation.ToMovieDetails -> {
                     navigator.navigate(MovieDetailsScreenDestination(movieId = effect.movieId))
                }
            }
        }.collect{}
    }

    LaunchedEffect(key1 = SIDE_EFFECTS_KEY){
        viewModel.loadCategories()
        viewModel.loadMovies()
    }

    // Render movies content
    MoviesContentContainer(
        state = viewModel.viewState.value,
        onEvent = viewModel::setEvent
    )
}