package com.example.awesomemoviesapp.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StandardProgressIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray.copy(alpha = 0.2f)) // Semi-transparent background for a cool overlay effect
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(50.dp)
                .background(Color.Transparent)
                .align(Alignment.Center) // Transparent background for the CircularProgressIndicator
        )
    }
}