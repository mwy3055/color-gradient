package com.practice.colorgradient.ui.home

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.practice.colorgradient.ui.theme.ColorGradientTheme
import com.practice.colorgradient.util.rotate
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.PI
import kotlin.math.atan
import kotlin.system.exitProcess

@Composable
fun RotatorWithContent(
    colors: List<Color>,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.extraLarge,
    content: @Composable BoxScope.() -> Unit = {},
) {
    // 별도의 언급이 없는 좌표는 Composable의 가운데를 기준으로 한다.
    // 회전할 각도
    var angle by remember { mutableStateOf(0.0) }
    // 드래그된 위치 (왼쪽 위 기준)
    var offset by remember { mutableStateOf(Offset.Zero) }
    // Composable의 왼쪽(오른쪽) 변의 중점
    var leftOffset by remember { mutableStateOf(Offset.Zero) }
    var rightOffset by remember { mutableStateOf(Offset.Infinite) }
    // 중점의 좌표 (왼쪽 위 기준)
    var centerOffset by remember { mutableStateOf(Offset.Zero) }
    // 그라디언트의 시작(끝) 지점 (왼쪽 위 기준)
    var startOffset by remember { mutableStateOf(Offset.Zero) }
    var endOffset by remember { mutableStateOf(Offset.Infinite) }

    val brush = Brush.linearGradient(
        colors = colors,
        start = startOffset,
        end = endOffset,
    )

    LaunchedEffect(true) {
        snapshotFlow { Triple(leftOffset, rightOffset, angle) }
            .collectLatest { (left, right, angle) ->
                startOffset = left.rotate(-angle) + centerOffset
                endOffset = right.rotate(-angle) + centerOffset
            }
    }

    Gradient(
        colors = colors,
        modifier = modifier
            .onSizeChanged { size ->
                val (width, height) = size.toSize()
                leftOffset = Offset(-width / 2, 0f)
                rightOffset = Offset(width / 2, 0f)
                centerOffset = Offset(width / 2, height / 2)
            }
            .pointerInput(true) {
                detectDragGestures { change, dragAmount ->
                    change.consume()

                    // 각도 계산, [-PI/2, PI/2] 라디안
                    val (centerX, centerY) = centerOffset
                    val (dragX, dragY) = change.position
                    val x = dragX - centerX
                    val y = centerY - dragY
                    val theta = atan(y.toDouble() / x)
                    // 각도 계산, [0, 360]도
                    angle = when {
                        x >= 0 && y >= 0 -> theta
                        x <= 0 -> theta + PI
                        x >= 0 && y <= 0 -> theta + 2 * PI
                        else -> exitProcess(1)
                    } * 360 / (2 * PI)
                    offset = Offset(x, y)
                }
            },
        brush = brush,
        shape = shape,
    ) {
        val isPreview = LocalInspectionMode.current
        if (isPreview) {
            Column {
                Text(text = "Angle = ${angle.toFloat()}")
                Text(text = "Start: $startOffset\nend: $endOffset")
            }
            Text(
                text = "test", modifier = Modifier
                    .align(Alignment.Center)
                    .rotate(-angle.toFloat())
            )
        } else {
            content()
        }
    }
}

@Preview
@Composable
private fun RotatorWithContentPreview() {
    ColorGradientTheme(dynamicColor = false) {
        RotatorWithContent(
            colors = gradientSampleColor,
            modifier = Modifier.size(width = 300.dp, height = 200.dp),
        )
    }
}