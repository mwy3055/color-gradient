package com.practice.colorgradient.util

import androidx.compose.ui.geometry.Offset
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Rotates this offset by [degree] to counter-clockwise orientation.
 */
fun Offset.rotate(degree: Double): Offset {
    val theta = 2 * PI * degree / 360
    val newX = x * cos(theta) - y * sin(theta)
    val newY = x * sin(theta) + y * cos(theta)
    return Offset(newX.toFloat(), newY.toFloat())
}

fun Offset.rotate(degree: Float): Offset = rotate(degree.toDouble())