package com.example.drutask.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.util.*
import com.example.drutask.theme.DruTheme
import com.example.drutask.R

@Composable
fun ErrorWithAction(
    title: String,
    body: String,
    buttonText: String,
    onNegative: () -> Unit,
    onPositive: () -> Unit,
    backgroundColor: Color = MaterialTheme.colors.background
) {
    Column(
        horizontalAlignment = Alignment.End
    ) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "Close",
            tint = DruTheme.colors.textPrimaryVariant,
            modifier = Modifier.clickable { onNegative() }
        )

        Error(title = title, body = body)

        Spacer(modifier = Modifier.padding(DruTheme.spacing.xl))

        FilledCharcoalGreyButton(
            text = buttonText,
            onClick = onPositive
        )
    }

}

@Composable
fun Error(
    modifier: Modifier = Modifier,
    title: String,
    body: String,
    backgroundColor: Color = MaterialTheme.colors.background,
    horizontalAlignment: Alignment.Horizontal = Alignment.End,
    verticalArrangement: Arrangement.Vertical = Arrangement.Center,
) {
    Column(
        horizontalAlignment = horizontalAlignment,
        verticalArrangement = verticalArrangement,
        modifier = modifier
            .padding(horizontal = 16.dp)
            .wrapContentHeight(),
    ) {

        MessageBody(
            text = title,
            modifier = Modifier.fillMaxWidth(),
            color = DruTheme.colors.textPrimary,
            style = DruTheme.typography.poppinsBold16
        )

        Spacer(modifier = Modifier.padding(DruTheme.spacing.l))

        MessageBody(
            text = body,
            modifier = Modifier.fillMaxWidth(),
            color = DruTheme.colors.textSecondary,
            style = DruTheme.typography.poppinsH3.copy(
                lineHeight = 18.sp
            )
        )
    }

}


@Composable
fun ShowError(error: AppException) {
    when (error) {
        is NoConnectionException -> {
            Error(
                title = stringResource(R.string.error),
                body = stringResource(id = R.string.internet_error),
            )
        }

        is TimeOutException -> {
            Error(
                title = stringResource(R.string.error),
                body = stringResource(id = R.string.timeout_error),
            )
        }

        is UnAuthorizedException -> {
            Error(
                title = stringResource(R.string.error),
                body = stringResource(id = R.string.app_error),
            )
        }

        is UnexpectedException -> {
            Error(
                title = stringResource(R.string.error),
                body = stringResource(id = R.string.app_error),
            )
        }

        else -> {
            Error(
                title = stringResource(R.string.error),
                body = stringResource(id = R.string.app_error),
            )
        }
    }
}