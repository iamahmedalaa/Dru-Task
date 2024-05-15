package com.example.drutask.features.movies

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.drutask.composables.PagingState
import com.example.drutask.theme.DruTheme
import com.example.presentaion.movies.contract.MoviesContract
import com.example.drutask.R

@Composable
fun MoviesContentContainer(
    state: MoviesContract.State,
    onEvent: (event: MoviesContract.Event) -> Unit
) {

    val horizontalPadding = 16.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = horizontalPadding)

        ) {

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(
                count = state.categories.size,
            ) { index ->
                val item = state.categories[index]
                item.let {
                    CategoryItem(
                        category = it,
                        onClick = {
                            onEvent(MoviesContract.Event.OnCategoryItemClick(it))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }

        // Space
        Spacer(modifier = Modifier.height(16.dp))
        val moviesState = state.movies.collectAsLazyPagingItems()

        when (moviesState.itemCount) {
            0 -> {
                // Handle Loading error state
                Column {
                    PagingState(
                        loadState = moviesState.loadState.mediator,
                        itemCount = moviesState.itemCount,
                        onRefresh = { moviesState.refresh() }
                    )
                }
            }

            else -> {
                // Movies list
                MoviesList(
                    movies = moviesState,
                    onItemClick = { _, item ->
                        onEvent(MoviesContract.Event.OnItemClick(item))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    prefixContent = {
                        // Subtitle text
                        Column {
                            Text(
                                text = stringResource(id = R.string.trending_movies),
                                style = DruTheme.typography.poppinsSemiBold16,
                                color = DruTheme.colors.textPrimary,
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .padding(top = 4.dp)
                                    .fillMaxWidth()
                            )

                            Text(
                                text = stringResource(id = R.string.trending_movies_desc),
                                style = DruTheme.typography.poppinsRegular14,
                                color = DruTheme.colors.textPrimary,
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    }
                )
            }
        }
    }
}
