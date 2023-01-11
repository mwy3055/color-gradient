package com.practice.colorgradient.ui.home

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.practice.colorgradient.ui.theme.ColorGradientTheme
import com.practice.colorgradient.util.closest
import com.practice.colorgradient.util.remainder
import com.practice.colorgradient.util.rotate
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.atan
import kotlin.system.exitProcess

/**
 * [RotatingGradient]의 상태를 나타내는 pure state class이다.
 * [RotatingGradient]와 관련된 모든 UI 로직은 이 클래스에 작성되어야 한다.
 *
 * 생성자가 아닌 [rememberRotatingState] 함수를 통해 객체를 얻어야 한다.
 */
@Stable
class RotatingState internal constructor(
    firstAngle: Int = 0,
    val animationSpec: AnimationSpec<Float> = tween(),
) {
    var angle by mutableStateOf(firstAngle)
        private set

    fun setAngleValue(angle: Int) {
        this.angle = angle.remainder(360)
    }

    suspend fun animateAngle(angle: Int) {
        val previousAngle = this.angle
        val targetAngle = previousAngle.closest(angle, angle + 360)
        animate(
            initialValue = previousAngle.toFloat(),
            targetValue = targetAngle.toFloat(),
            animationSpec = animationSpec,
        ) { value, velocity ->
            setAngleValue(value.toInt())
        }
    }
}

@Composable
fun rememberRotatingState(
    angle: Int = 0,
    animationSpec: AnimationSpec<Float> = tween()
) = remember(angle, animationSpec) {
    RotatingState(
        firstAngle = angle,
        animationSpec = animationSpec,
    )
}

@Composable
fun RotatingGradient(
    colors: List<Color>,
    modifier: Modifier = Modifier,
    rotatingState: RotatingState = rememberRotatingState(),
    shape: Shape = MaterialTheme.shapes.extraLarge,
    content: @Composable BoxScope.(Int) -> Unit = {},
) {
    // 별도의 언급이 없는 좌표는 Composable의 가운데를 기준으로 한다.
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
        snapshotFlow { Triple(leftOffset, rightOffset, rotatingState.angle) }
            .collectLatest { (left, right, angle) ->
                startOffset = left.rotate(-angle.toDouble()) + centerOffset
                endOffset = right.rotate(-angle.toDouble()) + centerOffset
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
                    val angle = when {
                        x >= 0 && y >= 0 -> theta
                        x <= 0 -> theta + PI
                        x >= 0 && y <= 0 -> theta + 2 * PI
                        else -> exitProcess(1)
                    } * 360 / (2 * PI)
                    rotatingState.setAngleValue(angle = angle.toInt())
                    offset = Offset(x, y)
                }
            },
        brush = brush,
        shape = shape,
    ) {
        content(rotatingState.angle)
    }
}

@Preview
@Composable
private fun RotatingGradientPreview() {
    val state = rememberRotatingState()
    val scope = rememberCoroutineScope()
    ColorGradientTheme(dynamicColor = false) {
        RotatingGradient(
            colors = gradientSampleColor,
            modifier = Modifier.size(width = 300.dp, height = 200.dp),
            rotatingState = state,
        ) { angle ->
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    imageVector = Icons.Outlined.ChevronRight,
                    contentDescription = null,
                    modifier = Modifier.rotate(-angle.toFloat()),
                    tint = contentColorFor(gradientSampleColor[0]),
                )
                Text(
                    text = "$angle",
                    style = MaterialTheme.typography.labelMedium,
                )
                Row {
                    IconButton(onClick = {
                        scope.launch {
                            state.animateAngle(0)
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Refresh,
                            contentDescription = null,
                        )
                    }
                    IconButton(onClick = {
                        scope.launch {
                            state.animateAngle(state.angle + 10)
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Add,
                            contentDescription = null,
                        )
                    }
                }
            }
        }
    }
}