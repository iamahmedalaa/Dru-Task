package com.example.drutask

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.example.drutask.composables.DefaultMessageDialog
import com.example.drutask.composables.Progress
import com.example.drutask.composables.ShowError
import com.example.drutask.features.NavGraphs
import com.example.drutask.theme.DruComposeTheme
import com.example.presentaion.base.IGlobalState
import com.ramcosta.composedestinations.DestinationsNavHost

@Composable
fun App(globalState: IGlobalState) {

    val navController = rememberNavController()

    DruComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            DestinationsNavHost(
                navGraph = NavGraphs.root,
                navController = navController,
                startRoute = NavGraphs.root.startRoute
            )

            if (globalState.loadingState.value) {
                Progress()
            }

            if (globalState.errorState.value != null) {
                globalState.errorState.value?.let {
                    ShowError (it)
                }
            }

            if (globalState.successState.value != null) {
                DefaultMessageDialog(
                    title = stringResource(R.string.success),
                    body = globalState.successState.value!!,
                    buttonText = stringResource(R.string.got_it),
                    onNegative = {
                        globalState.idle()
                    },
                    onPositive = {
                        globalState.idle()
                    }
                )
            }

        }
    }
}
