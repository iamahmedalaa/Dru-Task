package com.example.drutask.features.movies

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.drutask.composables.noRippleClickable
import com.example.drutask.theme.SmallRoundedCornerCard
import com.example.drutask.theme.DruTheme
import com.example.presentaion.movies.model.MovieCategoryUi

@Composable
fun CategoryItem(
    category: MovieCategoryUi,
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
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth()
        ) {

            category.name?.let {
                Text(
                    text = it,
                    style = DruTheme.typography.mencoBold16,
                    color = DruTheme.colors.textPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

