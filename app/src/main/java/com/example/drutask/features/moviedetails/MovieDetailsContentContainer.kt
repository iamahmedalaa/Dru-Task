package com.example.drutask.features.moviedetails

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.drutask.R
import com.example.drutask.composables.DruToolbar
import com.example.presentaion.moviedetails.contract.MovieDetailsContract

@Composable
fun MovieDetailsContentContainer(
    state: MovieDetailsContract.State,
    onEvent: (event: MovieDetailsContract.Event) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        BackHandler(enabled = true){
            onEvent(MovieDetailsContract.Event.BackClick)
        }

        // Toolbar
        DruToolbar(
            showBack = true,
            title = stringResource(id = R.string.movie_details),
            onBackClick = {
                onEvent(MovieDetailsContract.Event.BackClick)
            }
        )
        state.movie?.let {
            MovieDetail(movie = it)
        }
    }

}