package com.soyaeeb.loginpagedesigncompose.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import kotlin.time.seconds

@Composable
fun LoadingScreen() {
    var show by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        delay(100000L)
        show = false
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if(show){
            CircularProgressIndicator()
        }

    }
}
