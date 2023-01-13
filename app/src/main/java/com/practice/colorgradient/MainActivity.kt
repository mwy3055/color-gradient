package com.practice.colorgradient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.practice.colorgradient.ui.home.HomeScreen
import com.practice.colorgradient.ui.theme.ColorGradientTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColorGradientTheme {
                HomeScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}