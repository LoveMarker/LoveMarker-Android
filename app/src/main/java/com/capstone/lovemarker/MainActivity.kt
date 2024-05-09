package com.capstone.lovemarker

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import com.capstone.lovemarker.ui.theme.LoveMarkerTheme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 화면이 꺼지지 않도록
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // 화면을 가로 모드로 고정
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        // 라이프사이클 이벤트 옵저버 등록
        lifecycle.addObserver(viewModel)

        setContent {
            LoveMarkerTheme {
                TiltScreen(x = viewModel.x.value, y = viewModel.y.value)
            }
        }
    }
}

@Composable
fun TiltScreen(
    x: Float,
    y: Float
) {
    val yCoord = x * 20
    val xCoord = y * 20

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        val centerX = size.width / 2
        val centerY = size.height / 2

        // 가운데 원
        drawCircle(
            color = Color.Black,
            radius = 100f,
            center = Offset(centerX, centerY),
            style = Stroke()
        )

        // 녹색 원
        drawCircle(
            color = Color.Green,
            radius = 100f,
            center = Offset(xCoord + centerX, yCoord + centerY),
        )

        // 가운데 십자가 모양
        drawLine(
            color = Color.Black,
            start = Offset(centerX - 20, centerY),
            end = Offset(centerX + 20, centerY)
        )
        drawLine(
            color = Color.Black,
            start = Offset(centerX, centerY - 20),
            end = Offset(centerX, centerY + 20)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TiltPreview(modifier: Modifier = Modifier) {
    TiltScreen(x = 30f, y = 20f)
}