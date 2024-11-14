package com.capstone.lovemarker.feature.matching.sender

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import com.capstone.lovemarker.feature.matching.R

@Composable
fun SenderScreen(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = LoveMarkerTheme.colorScheme.surfaceContainer
    ) {
        Column {
            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "navigate up"
                )
            }
            Column(
                modifier = Modifier.padding(
                    horizontal = 24.dp,
                    vertical = 42.dp
                )
            ) {
                Text(
                    text = stringResource(R.string.matching_sender_anniversary_title),
                    style = LoveMarkerTheme.typography.headline20B,
                )
                Text(
                    text = stringResource(R.string.matching_sender_guide_detail),
                    style = LoveMarkerTheme.typography.label13M,
                    color = LoveMarkerTheme.colorScheme.onSurface700,
                    modifier = Modifier.padding(top = 13.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SenderPreview() {
    LoveMarkerTheme {
        SenderScreen(
            navigateUp = {}
        )
    }
}