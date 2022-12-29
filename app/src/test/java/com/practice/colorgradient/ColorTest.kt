package com.practice.colorgradient

import androidx.compose.ui.graphics.Color
import com.practice.colorgradient.util.toArgbString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ColorTest {

    @Test
    fun colorToHex() {
        val color = Color(0xFFC5F8AD)
        assertThat(color.toArgbString()).isEqualTo("FFC5F8AD")
    }
}