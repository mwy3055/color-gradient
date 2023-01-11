package com.practice.colorgradient

import com.practice.colorgradient.util.closest
import com.practice.colorgradient.util.remainder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class NumberTest {

    @Test
    fun remainderTest_inBounds() {
        assertThat(50.remainder(360)).isEqualTo(50)
    }

    @Test
    fun remainderTest_outOBounds() {
        assertThat(370.remainder(360)).isEqualTo(10)
    }

    @Test
    fun remainderTest_iNegative() {
        assertThat((-359).remainder(360)).isEqualTo(1)
    }

    @Test
    fun remainderTest_iNegative2() {
        assertThat((-361).remainder(360)).isEqualTo(359)
    }

    @Test
    fun closestTest_simple() {
        assertThat(180.closest(0, 190)).isEqualTo(190)
    }

    @Test
    fun closestTest_simple2() {
        assertThat(10.closest(0, 190)).isEqualTo(0)
    }

    @Test
    fun closestTest_outOBounds_toLet() {
        assertThat(0.closest(10, 180)).isEqualTo(10)
    }

    @Test
    fun closestTest_outOBounds_toRight() {
        assertThat(190.closest(0, 180)).isEqualTo(180)
    }

    @Test
    fun closestTest_middle() {
        assertThat(90.closest(0, 180)).isEqualTo(0)
    }
}