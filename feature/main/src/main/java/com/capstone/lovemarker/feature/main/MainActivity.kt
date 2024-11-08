package com.capstone.lovemarker.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navigator = rememberMainNavigator()
            LoveMarkerTheme {
                MainScreen(
                    navigator = navigator
                )
            }
        }
    }
}