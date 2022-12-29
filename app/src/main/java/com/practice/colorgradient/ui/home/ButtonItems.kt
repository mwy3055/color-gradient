package com.practice.colorgradient.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.practice.colorgradient.ui.theme.ColorGradientTheme

data class ButtonState(
    val icon: ImageVector,
    val text: String,
    val onClick: () -> Unit,
)

@Composable
fun ButtonItems(
    buttons: List<ButtonState>,
    modifier: Modifier = Modifier,
    arrangement: Arrangement.Horizontal = Arrangement.Start,
    spacing: Dp = 0.dp,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = arrangement,
    ) {
        buttons.forEach { button ->
            ButtonItem(
                button = button,
                modifier = Modifier.padding(
                    start = if (button == buttons.first()) 0.dp else spacing / 2,
                    end = if (button == buttons.last()) 0.dp else spacing / 2,
                ),
            )
        }
    }
}

@Composable
fun ButtonItem(
    button: ButtonState,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        onClick = button.onClick,
        modifier = modifier,
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 10.dp,
            end = 24.dp,
            bottom = 10.dp,
        ),
    ) {
        Icon(
            imageVector = button.icon,
            contentDescription = button.text,
            modifier = Modifier.padding(end = 8.dp),
        )
        Text(
            text = button.text,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ButtonItemsPreview() {
    val buttons = listOf(
        ButtonState(
            icon = Icons.Default.Share,
            text = "Share",
            onClick = {},
        ),
        ButtonState(
            icon = Icons.Default.SaveAlt,
            text = "Save",
            onClick = {},
        ),
    )

    ColorGradientTheme(dynamicColor = false) {
        ButtonItems(
            buttons = buttons,
            modifier = Modifier.wrapContentWidth(),
            arrangement = Arrangement.Center,
            spacing = 16.dp,
        )
    }
}