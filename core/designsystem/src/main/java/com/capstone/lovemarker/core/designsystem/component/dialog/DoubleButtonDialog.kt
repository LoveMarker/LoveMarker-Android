package com.capstone.lovemarker.core.designsystem.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
fun DoubleButtonDialog(
    title: String,
    description: String,
    confirmButtonText: String,
    dismissButtonText: String,
    onConfirmButtonClick: () -> Unit,
    onDismissButtonClick: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissButtonClick
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
                    style = LoveMarkerTheme.typography.body16M,
                    textAlign = TextAlign.Center,
                    color = Gray600,
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    LoveMarkerButton(
                        onClick = onDismissButtonClick,
                        buttonText = dismissButtonText,
                        modifier = Modifier.weight(1f)
                    )
                    LoveMarkerButton(
                        onClick = onConfirmButtonClick,
                        buttonText = confirmButtonText,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun DoubleButtonDialogPreview() {
    LoveMarkerTheme {
        DoubleButtonDialog(
            title = "상대방에게 코드를 공유해주세요",
            description = "DFE8138fdfaUi",
            dismissButtonText = "취소",
            confirmButtonText = "공유",
            onConfirmButtonClick = {},
            onDismissButtonClick = {}
        )
    }
}