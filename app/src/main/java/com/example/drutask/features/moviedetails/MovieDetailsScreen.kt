package com.example.drutask.features.moviedetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.presentaion.base.SIDE_EFFECTS_KEY
import com.example.presentaion.moviedetails.contract.MovieDetailsContract
import com.example.presentaion.moviedetails.viewmodel.MovieDetailsViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.onEach

@Destination
@Composable
fun MovieDetailsScreen(
    navigator: DestinationsNavigator,
    movieId: Long? = null,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {

    // Handle side effects
    LaunchedEffect(SIDE_EFFECTS_KEY) {
        viewModel.effect.onEach { effect ->
            when (effect) {
            MovieDetailsContract.Effect.Navigation.GoBack -> navigator.navigateUp()
            }
        }.collect{}
    }

    // Init view model
    LaunchedEffect(SIDE_EFFECTS_KEY) {
        viewModel.init(
            movieId = movieId,
        )
    }

    // Then render content
    MovieDetailsContentContainer(
        state = viewModel.viewState.value,
        onEvent = viewModel::setEvent
    )

}
