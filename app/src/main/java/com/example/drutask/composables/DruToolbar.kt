package com.example.drutask.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.drutask.R


@Composable
fun DruToolbar(
    modifier: Modifier = Modifier,
    title: String? = null,
    showBack: Boolean = false,
    onBackClick: (() -> Unit)? = null,
    showClose: Boolean = false,
    onCloseClick: (() -> Unit)? = null,
    endButtonsContent: (@Composable () -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 12.dp, start = 24.dp, end = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Back icon
            if (showBack) {
                Image(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                    modifier = Modifier
                        .size(36.dp)
                        .padding(top = 18.dp, end = 18.dp)
                        .noRippleClickable {
                            onBackClick?.invoke()
                        }
                )
            }

            Spacer(
                modifier = Modifier
                    .height(24.dp)
                    .weight(1f)
            )

            // Close icon
            if (showClose) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_close_circle),
                    contentDescription = null,
                    modifier = Modifier
                        .size(36.dp)
                        .padding(top = 12.dp, start = 12.dp)
                        .noRippleClickable {
                            onCloseClick?.invoke()
                        }
                )
            }

            // Render end buttons content if exists
            endButtonsContent?.invoke()
        }

        if (title != null) {
            ScreenTitle(text = title)
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun DruToolbarToolbarPreview() {
    DruToolbar(
        title = "Add Room",
        showBack = true,
        showClose = true
    )
}
