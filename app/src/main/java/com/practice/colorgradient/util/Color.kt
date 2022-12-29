package com.practice.colorgradient.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

fun Color.toArgbString() = "%X".format(this.toArgb()).takeLast(8)