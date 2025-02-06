package com.dxgabalt.tupincha


import androidx.compose.material.*
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.ScaleTransition
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Navigator(screen = LoginScreen()) { navigator ->
            ScaleTransition(navigator)
        }
    }
}


