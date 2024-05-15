package com.example.drutask.features.moviedetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.drutask.composables.GrayRemoteImage
import com.example.drutask.composables.KeyValueTV
import com.example.drutask.theme.DruTheme
import com.example.drutask.theme.SmallRoundedCornerImage
import com.example.presentaion.moviedetails.model.MovieDetailsUiModel
import com.example.drutask.R

@Composable
fun MovieDetail(
    modifier: Modifier = Modifier,
    movie: MovieDetailsUiModel,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(
                start = 16.dp,
                end = 16.dp,
            )
    ) {

        Row(modifier = modifier) {
            // Poster
            GrayRemoteImage(
                imageUrl = movie.posterPath,
                description = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(end = DruTheme.spacing.m)
                    .requiredWidth(180.dp)
                    .requiredHeight(250.dp)
                    .clip(SmallRoundedCornerImage)
            )

            // Meta
            Column(
                modifier = Modifier.padding(start = 12.dp)
            ) {

                // Rating
                movie.voteAverage?.let {
                    KeyValueTV(
                        key = stringResource(id = R.string.rating),
                        value = movie.voteAverage.toString()
                    )
                }

                // Duration
                movie.runtime?.let {
                    KeyValueTV(
                        key = stringResource(id = R.string.duration),
                        value = movie.runtime.toString()
                            .plus(stringResource(id = R.string.minutes))
                    )
                }

                // Release Date
                movie.releaseDate?.let {
                    KeyValueTV(
                        key = stringResource(id = R.string.release_date),
                        value = it
                    )
                }

                // Genre
                movie.genres?.joinToString(separator = ", ")
                    ?.let {
                        KeyValueTV(
                            key = stringResource(
                                id = R.string.geners
                            ),
                            value = it
                        )
                    }
            }
        }

        // Title
        movie.title?.let {
            Text(
                text = it,
                modifier = Modifier.padding(
                    top = 10.dp,
                    bottom = 4.dp
                ),
                style = DruTheme.typography.mencoBold18,
                color = DruTheme.colors.textPrimary,
            )
        }

        // Desc
        movie.overview?.let {
            Text(
                text = it,
                modifier = Modifier.padding(
                    bottom = 10.dp
                ),
                style = DruTheme.typography.poppinsRegular14,
                color = DruTheme.colors.textSecondary,
            )
        }

    }
}

@Preview
@Composable
fun MovieDetailPreview() {
    MovieDetail(
        movie = MovieDetailsUiModel()
    )
}