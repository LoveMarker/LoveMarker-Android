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
    onClick: () -> Unit,
    onDismiss: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = onDismiss
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
                if (description.isNotEmpty()) {
                    Text(
                        text = description,
                        style = LoveMarkerTheme.typography.body14M,
                        textAlign = TextAlign.Center,
                        color = Gray600,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                }
                LoveMarkerButton(
                    onClick = onClick,
                    buttonText = buttonText,
                    enabled = true,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
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
            title = "커플 연결이 이미 해제되어 있습니다",
            description = "",
            buttonText = "확인",
            onClick = {}
        )
    }
}