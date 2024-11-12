package com.capstone.lovemarker.core.designsystem.component.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capstone.lovemarker.core.designsystem.theme.Gray700
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme

@Composable
fun LoveMarkerSnackbar(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(color = Gray700)
                .fillMaxWidth()
                .height(48.dp)
                .padding(vertical = 14.dp, horizontal = 16.dp)
        ) {
            Text(
                text = message,
                style = LoveMarkerTheme.typography.body14M,
                color = Color.White,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoveMarkerSnackbarPreview() {
    LoveMarkerTheme {
        LoveMarkerSnackbar(message = "업로드에 실패했습니다.")
    }
}