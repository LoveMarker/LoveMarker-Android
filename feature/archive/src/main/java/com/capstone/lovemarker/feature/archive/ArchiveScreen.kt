package com.capstone.lovemarker.feature.archive

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme

@Composable
fun ArchiveRoute(modifier: Modifier = Modifier) {

}

@Composable
fun ArchiveScreen(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "archive screen")
    }
}

@Preview(showBackground = true)
@Composable
private fun ArchivePreview() {
    LoveMarkerTheme {
        ArchiveScreen()
    }
}
