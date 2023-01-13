package com.practice.colorgradient.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.practice.colorgradient.ui.theme.ColorGradientTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val systemUiController = rememberSystemUiController()
    val statusBarColor = MaterialTheme.colorScheme.primaryContainer
    LaunchedEffect(key1 = true) {
        systemUiController.setNavigationBarColor(statusBarColor)
    }

    // TODO: move data to ViewModel or any state holder
    val colors = listOf(
        Color(0xFFC5F8AD),
        Color(0xFF3ADCFF),
    )
    val buttons = listOf(
        ButtonState(
            icon = Icons.Default.Share,
            text = "Share",
            onClick = {},
        ),
        ButtonState(
            icon = Icons.Default.SaveAlt,
            text = "Save",
            onClick = {},
        ),
    )

    val rotatingState = rememberRotatingState()

    Scaffold(
        modifier = modifier,
        topBar = {
            HomeScreenTopAppBar()
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            RotatingIndicatorGradient(
                colors = colors,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
                    .weight(3f),
                rotatingState = rotatingState,
            )
            ColorItems(
                colors = colors,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                itemSpacing = 40.dp,
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                ButtonItems(
                    buttons = buttons,
                    modifier = Modifier.align(Alignment.Center),
                    arrangement = Arrangement.Center,
                    spacing = 20.dp,
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.5f),
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    ColorGradientTheme(dynamicColor = false) {
        HomeScreen(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
        )
    }
}