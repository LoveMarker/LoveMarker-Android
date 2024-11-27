package com.capstone.lovemarker.core.designsystem.component.appbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.capstone.lovemarker.core.designsystem.component.snackbar.LoveMarkerSnackbar
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import com.capstone.lovemarker.core.designsystem.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoveMarkerTopAppBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = com.capstone.lovemarker.core.designsystem.R.string.app_name),
                fontFamily = FontFamily(Font(resId = com.capstone.lovemarker.core.designsystem.R.font.ribeye_regular)),
                fontSize = 18.sp
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = White
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
private fun TopAppBarPreview() {
    LoveMarkerTheme {
        LoveMarkerTopAppBar()
    }
}
