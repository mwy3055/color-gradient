package com.practice.colorgradient.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.practice.colorgradient.util.toArgbString

@Composable
fun ColorItems(
    colors: List<Color>,
    modifier: Modifier = Modifier,
    itemShape: Shape = MaterialTheme.shapes.small,
    itemSpacing: Dp = 20.dp,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
    ) {
        colors.forEach { color ->
            ColorItem(
                color = color,
                shape = itemShape,
                modifier = Modifier
                    .padding(
                        start = if (color == colors.first()) 0.dp else itemSpacing / 2,
                        end = if (color == colors.last()) 0.dp else itemSpacing / 2,
                    )
                    .width(90.dp),
            )
        }
    }
}

@Composable
fun ColorItem(
    color: Color,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.small,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(shape)
                .background(color = color),
        )
        Text(
            text = "#${color.toArgbString()}",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(10.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
