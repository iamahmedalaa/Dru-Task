package com.example.drutask.features.movies

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.drutask.R
import com.example.drutask.composables.GrayRemoteImage
import com.example.drutask.composables.KeyValueRowTV
import com.example.drutask.composables.noRippleClickable
import com.example.presentaion.movies.model.MovieUiModel
import com.example.drutask.theme.SmallRoundedCornerCard
import com.example.drutask.theme.SmallRoundedCornerImage
import com.example.drutask.theme.DruTheme

@Composable
fun MoviesListItem(
    movie: MovieUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        contentColor = DruTheme.colors.white,
        elevation = 0.dp,
        shape = SmallRoundedCornerCard,
        border = BorderStroke(
            width = DruTheme.spacing.unit,
            color = DruTheme.colors.stroke
        ),
        modifier = modifier
            .fillMaxWidth()
            .noRippleClickable(onClick = onClick)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
                .fillMaxWidth()
        ) {

            // Contractor image
            GrayRemoteImage(
                imageUrl = movie.posterPath,
                description = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(horizontal = DruTheme.spacing.m)
                    .size(70.dp)
                    .clip(SmallRoundedCornerImage)
            )

            // Texts column
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(DruTheme.spacing.extra_xxs)
            ) {
                // Title text
                movie.title?.let {
                    Text(
                        text = it,
                        style = DruTheme.typography.mencoBold16,
                        color = DruTheme.colors.textPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // overview text
                movie.overview?.let {
                    Text(
                        text = it,
                        style = DruTheme.typography.poppinsRegular14,
                        color = DruTheme.colors.middleGray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Release Date
                movie.releaseDate?.let {
                    KeyValueRowTV(
                        key = stringResource(id = R.string.release_date),
                        value = it
                    )
                }
            }

            // Arrow icon
            Image(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                modifier = Modifier
                    .height(14.dp)
                    .padding(end = DruTheme.spacing.m),
            )
        }
    }
}

@Preview
@Composable
fun MoviesListItemPreview() {
    MoviesListItem(
        movie = MovieUiModel(
             id = 0,
             title = "Contractor Contracting LLC.",
             overview = "Los Angeles, CA",
             releaseDate = "2023",
         ),
         onClick = {},
     )
}
