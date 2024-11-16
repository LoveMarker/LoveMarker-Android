package com.capstone.lovemarker.core.designsystem.component.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.capstone.lovemarker.core.designsystem.component.button.LoveMarkerButton
import com.capstone.lovemarker.core.designsystem.theme.Black
import com.capstone.lovemarker.core.designsystem.theme.Gray600
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import com.capstone.lovemarker.core.designsystem.theme.White

@Composable
fun SingleButtonDialog(
    title: String,
    description: String,
    buttonText: String,
    onConfirmButtonClick: () -> Unit,
) {
    Dialog(
        onDismissRequest = onConfirmButtonClick
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = White
            ),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = LoveMarkerTheme.typography.headline18B,
                    color = Black,
                    modifier = Modifier.padding(top = 24.dp)
                )
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = description,
                    style = LoveMarkerTheme.typography.body14M,
                    textAlign = TextAlign.Center,
                    color = Gray600,
                )
                Spacer(modifier = Modifier.height(6.dp))
                LoveMarkerButton(
                    onClick = onConfirmButtonClick,
                    buttonText = buttonText
                )
            }
        }
    }
}

@Preview
@Composable
private fun SingleButtonDialogPreview() {
    LoveMarkerTheme {
        SingleButtonDialog(
            title = "커플 연결이 필요해요",
            description = "LoveMarker 기능은\n커플 연결 후 사용할 수 있어요",
            buttonText = "매칭하러 가기",
            onConfirmButtonClick = {}
        )
    }
}