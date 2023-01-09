package com.practice.colorgradient.util

import kotlin.math.abs

fun Float.similar(other: Float) = abs(this - other) <= 1e9