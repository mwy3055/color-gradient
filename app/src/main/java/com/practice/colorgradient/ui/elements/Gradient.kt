package com.practice.colorgradient.ui.elements

import androidx.compose.foundation.Canvas
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
    shape: Shape = MaterialTheme.shapes.extraLarge,
) {
    Canvas(modifier = modifier.clip(shape)) {
        drawRect(brush = Brush.verticalGradient(colors = colors))
    }
}

@Preview(showBackground = true)
@Composable
private fun GradientPreview() {
    ColorGradientTheme(dynamicColor = false) {
        Gradient(
            colors = listOf(Color(0xFFC5F8AD), Color(0xFF3ADCFF)),
            modifier = Modifier
                .size(width = 330.dp, height = 265.dp)
                .padding(10.dp),
        )
    }
}