package com.practice.colorgradient.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practice.colorgradient.ui.theme.ColorGradientTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RotatingIndicatorGradient(
    colors: List<Color>,
    modifier: Modifier = Modifier,
    rotatingState: RotatingState = rememberRotatingState(),
    shape: Shape = MaterialTheme.shapes.extraLarge,
    enterTransition: EnterTransition = fadeIn() + expandIn(expandFrom = Alignment.Center),
    exitTransition: ExitTransition = fadeOut() + shrinkOut(shrinkTowards = Alignment.Center),
) {
    var isFocused by rememberSaveable { mutableStateOf(false) }
    val iconColor = contentColorFor(colors[colors.size / 2])

    RotatingGradient(
        colors = colors,
        modifier = modifier.pointerInput(isFocused) {
            awaitPointerEventScope {
                isFocused = if (isFocused) {
                    // 모든 터치가 떼어질 때까지 기다림
                    while (waitForUpOrCancellation() == null);
                    false
                } else {
                    awaitFirstDown(false)
                    true
                }
            }
        },
        rotatingState = rotatingState,
        shape = shape,
    ) {
        AnimatedVisibility(
            visible = isFocused,
            modifier = Modifier
                .align(Alignment.Center)
                .rotate(-rotatingState.angle.toFloat()),
            enter = enterTransition,
            exit = exitTransition,
        ) {
            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = iconColor,
            )
        }
    }
}

@Preview
@Composable
private fun RotatingIndicatorGradientPreview() {
    ColorGradientTheme(dynamicColor = false) {
        RotatingIndicatorGradient(
            colors = gradientSampleColor,
            modifier = Modifier.size(width = 300.dp, height = 200.dp),
        )
    }
}