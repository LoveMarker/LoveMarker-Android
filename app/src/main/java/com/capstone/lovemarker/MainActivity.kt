package com.capstone.lovemarker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.capstone.lovemarker.ui.theme.LoveMarkerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LoveMarkerTheme {
                val viewModel = viewModel<MainViewModel>()
                val navController = rememberNavController()
                val bmi = viewModel.bmi.value

                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable(route = "home") {
                        HomeScreen { height, weight ->
                            // bmi state 변경 -> recomposition -> 결과 화면으로 bmi 전달
                            viewModel.calcBMI(height, weight)
                            navController.navigate("result")
                        }
                    }
                    composable(route = "result") {
                        ResultScreen(navController, bmi)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onResultClick: (Double, Double) -> Unit
) {
    val (height, setHeight) = rememberSaveable {
        mutableStateOf("")
    }

    val (weight, setWeight) = rememberSaveable {
        mutableStateOf("")
    }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("비만도 계산기") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = height,
                onValueChange = setHeight,
                label = { Text("키") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                maxLines = 1
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = weight,
                onValueChange = setWeight,
                label = { Text("몸무게") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                }),
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                modifier = Modifier.align(Alignment.End),
                onClick = {
                    if (height.isNotBlank() and weight.isNotBlank()) {
                        keyboardController?.hide()
                        onResultClick(height.toDouble(), weight.toDouble())
                    }
                }
            ) {
                Text("결과")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    navController: NavHostController,
    bmi: Double
) {
    val result = when {
        bmi >= 35 -> "고도 비만"
        bmi >= 30 -> "2단계 비만"
        bmi >= 25 -> "1단계 비만"
        bmi >= 23 -> "과체중"
        bmi >= 18.5 -> "정상"
        else -> "저체중"
    }

    val imgRes = when {
        bmi >= 23 -> R.drawable.ic_sentiment_very_dissatisfied
        bmi >= 18.5 -> R.drawable.ic_sentiment_satisfied
        else -> R.drawable.ic_sentiment_dissatisfied
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("비만도 계산기") },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "home",
                        modifier = Modifier
                            .padding(6.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = result,
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.height(50.dp))
            Image(
                painter = painterResource(id = imgRes),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = Color.Blue),
                modifier = Modifier.size(100.dp)
            )
        }
    }
}