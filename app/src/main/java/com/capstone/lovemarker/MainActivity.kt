package com.capstone.lovemarker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.lovemarker.ui.theme.LoveMarkerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LoveMarkerTheme {
                val viewModel = viewModel<MainViewModel>()

                val sec = viewModel.sec.value
                val millis = viewModel.millis.value
                val isRunning = viewModel.isRunning.value
                val lapTimes = viewModel.lapTimes.value

                MainScreen(
                    sec = sec,
                    millis = millis,
                    isRunning = isRunning,
                    lapTimes = lapTimes,
                    onReset = { viewModel.reset() },
                    onToggle = { running ->
                        if (running) {
                            viewModel.pause()
                        } else {
                            viewModel.start()
                        }
                    },
                    onLapTime = { viewModel.recordLapTime() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    sec: Int,
    millis: Int,
    isRunning: Boolean,
    lapTimes: List<String>,
    onReset: () -> Unit,
    onToggle: (Boolean) -> Unit,
    onLapTime: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text("Stop Watch")
            })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text("$sec", fontSize = 100.sp)
                Text("$millis")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .weight(1F) // 수직 방향으로 남은 영역 모두 차지
                    .verticalScroll(rememberScrollState())
            ) {
                lapTimes.forEach { time ->
                    Text(time)
                }
            }

            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FloatingActionButton(
                    onClick = { onReset() },
                    containerColor = Color.Red
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_refresh_24),
                        contentDescription = "reset"
                    )
                }

                FloatingActionButton(
                    onClick = { onToggle(isRunning) },
                    containerColor = Color.Green
                ) {
                    Image(
                        painter = painterResource(
                            id =
                            if (isRunning) R.drawable.ic_pause_24
                            else R.drawable.ic_play_arrow_24
                        ),
                        contentDescription = "start or pause"
                    )
                }

                Button(
                    onClick = { onLapTime() }
                ) {
                    Text("랩 타임")
                }
            }
        }
    }
}

@Preview
@Composable
fun MainPreview(modifier: Modifier = Modifier) {

}