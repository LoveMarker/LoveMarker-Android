package com.capstone.lovemarker.feature.matching.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.lovemarker.core.designsystem.component.button.LoveMarkerButton
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import com.capstone.lovemarker.feature.matching.R

@Composable
fun MatchingScreen(
    onCreateButtonClick: () -> Unit,
    onInputButtonClick: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = LoveMarkerTheme.colorScheme.surfaceContainer
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(id = com.capstone.lovemarker.core.designsystem.R.string.app_name),
                fontFamily = FontFamily(Font(resId = com.capstone.lovemarker.core.designsystem.R.font.ribeye_regular)),
                fontSize = 40.sp,
                modifier = Modifier.padding(bottom = 120.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.img_matching_home),
                contentDescription = stringResource(R.string.matching_img_desc),
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(1f))
            LoveMarkerButton(
                onClick = onCreateButtonClick,
                buttonText = "초대 코드 생성하기",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            )
            LoveMarkerButton(
                onClick = onInputButtonClick,
                buttonText = "초대 코드 입력하기",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            )
            Spacer(modifier = Modifier.height(55.dp))
        }
    }
}

@Preview
@Composable
private fun MatchingPreview() {
    LoveMarkerTheme {
        MatchingScreen(
            onCreateButtonClick = {},
            onInputButtonClick = {}
        )
    }
}