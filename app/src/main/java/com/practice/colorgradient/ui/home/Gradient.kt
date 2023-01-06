package com.practice.colorgradient.ui.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practice.colorgradient.ui.theme.ColorGradientTheme

@Composable
fun Gradient(
    colors: List<Color>,
    modifier: Modifier = Modifier,
    brush: Brush = Brush.horizontalGradient(colors = colors),
    shape: Shape = MaterialTheme.shapes.extraLarge,
    content: @Composable BoxScope.() -> Unit = {},
) {
    Box(modifier = modifier.clip(shape)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(brush = brush)
        }
        content()
    }
}

val gradientSampleColor = listOf(Color(0xFFC5F8AD), Color(0xFF3ADCFF))

@Preview(showBackground = true)
@Composable
private fun GradientPreview() {
    ColorGradientTheme(dynamicColor = false) {
        Gradient(
            colors = gradientSampleColor,
            modifier = Modifier
                .size(width = 330.dp, height = 265.dp)
                .padding(10.dp),
        )
    }
}