package com.capstone.lovemarker.feature.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme

@Composable
fun MapRoute(
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    MapScreen()
}

@Composable
fun MapScreen(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "map screen")
    }
}

@Preview(showBackground = true)
@Composable
private fun MapPreview() {
    LoveMarkerTheme {
        MapScreen()
    }
}
