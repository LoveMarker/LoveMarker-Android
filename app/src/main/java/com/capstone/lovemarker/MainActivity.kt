package com.capstone.lovemarker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.lovemarker.domain.util.TodoAndroidViewModelFactory
import com.capstone.lovemarker.ui.main.MainScreen
import com.capstone.lovemarker.ui.main.MainViewModel
import com.capstone.lovemarker.ui.theme.LoveMarkerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LoveMarkerTheme {
                val viewModel: MainViewModel = viewModel(
                    factory = TodoAndroidViewModelFactory(application)
                )
                MainScreen(viewModel = viewModel)
            }
        }
    }
}
