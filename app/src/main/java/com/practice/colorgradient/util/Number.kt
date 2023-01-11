package com.practice.colorgradient.util

import kotlin.math.abs
import kotlin.math.floor

fun Float.similar(other: Float) = abs(this - other) <= 1e9

/**
 * 주어진 값을 0 이상 [maximumValue] 이하인 구간에 넣는다.
 * 예를 들어 `50.remainder(360)`는 `50`을, `-359f.remainder(360f)`는 `1`을 반환한다.
 *
 * @param maximumValue 구간의 최댓값
 */
fun Int.remainder(maximumValue: Int): Int {
    val nonNegative = if (this >= 0) this else {
        val quotient = floor(this.toDouble() / maximumValue).toInt()
        this + maximumValue * abs(quotient)
    }
    return nonNegative % maximumValue
}

/**
 * 주어진 두 값 중 더 가까운 값을 반환한다. 차이가 같은 경우에는 [f1]을 반환한다.
 *
 * @param f1 비교할 수
 * @param f2 비교할 수
 */
fun Int.closest(f1: Int, f2: Int): Int {
    val diff1 = abs(this - f1)
    val diff2 = abs(this - f2)
    return if (diff1 <= diff2) f1 else f2
}