package com.practice.colorgradient

import androidx.compose.ui.geometry.Offset
import com.practice.colorgradient.util.rotate
import com.practice.colorgradient.util.similar
import org.junit.jupiter.api.Test
import kotlin.math.PI
import kotlin.math.sin

class OffsetTest {

    @Test
    fun rotateTest() {
        val offset = Offset(1f, 0f)
        val sin = sin(PI / 4).toFloat()
        assertEquals(Offset(0f, 1f), offset.rotate(90.0))
        assertEquals(Offset(-1f, 0f), offset.rotate(180.0))
        assertEquals(Offset(0f, -1f), offset.rotate(270.0))
        assertEquals(offset, offset.rotate(360.0))
        assertEquals(Offset(sin, sin), offset.rotate(45.0))
    }

    private fun assertEquals(expected: Offset, actual: Offset) {
        assert(expected.x.similar(actual.x) && expected.y.similar(actual.y)) {
            "Expected: $expected\n  Actual: $actual"
        }
    }
}