package com.practice.colorgradient

import com.practice.colorgradient.ui.home.RotatingState
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RotatingStateTest {

    @Test
    fun setAngleTest_simpleSet() {
        val state = RotatingState(firstAngle = 1)
        state.setAngleValue(2)
        assertThat(state.angle).isEqualTo(2)
    }
}