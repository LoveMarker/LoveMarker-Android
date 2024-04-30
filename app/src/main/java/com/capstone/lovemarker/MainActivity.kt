package com.capstone.lovemarker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.SnackbarHost
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // destructuring (구조 분해)
            val (text, setValue) = remember {
                mutableStateOf("")
            }

            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()
            val keyboardController = LocalSoftwareKeyboardController.current

            Scaffold(
                snackbarHost = { SnackbarHost(snackbarHostState) },
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = text,
                        onValueChange = setValue
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = {
                        keyboardController?.hide()
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = text,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }) {
                        Text("Click")
                    }
                }
            }
        }
    }
}

