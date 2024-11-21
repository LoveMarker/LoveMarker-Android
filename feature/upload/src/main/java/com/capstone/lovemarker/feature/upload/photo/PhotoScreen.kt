package com.capstone.lovemarker.feature.upload.photo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capstone.lovemarker.core.designsystem.component.button.LoveMarkerButton
import com.capstone.lovemarker.core.designsystem.theme.Beige400
import com.capstone.lovemarker.core.designsystem.theme.Gray800
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import com.capstone.lovemarker.feature.upload.R

@Composable
fun PhotoRoute(
    navigateUp: () -> Unit,
) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoScreen(
    navigateUp: () -> Unit,
    photoSelected: Boolean
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CenterAlignedTopAppBar(
            navigationIcon = {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.upload_navigate_up_btn_desc)
                    )
                }
            },
            title = {
                Text(
                    text = stringResource(R.string.upload_title),
                    style = LoveMarkerTheme.typography.body16B,
                )
            }
        )
        Spacer(modifier = Modifier.padding(top = 14.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = stringResource(R.string.upload_photo_guide_text),
                style = LoveMarkerTheme.typography.label13M,
                color = Gray800
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "\n0/5",
                style = LoveMarkerTheme.typography.label13M,
                color = Gray800,
            )
        }
        Spacer(modifier = Modifier.padding(top = 18.dp))
        Row {
            Spacer(modifier = Modifier.padding(start = 24.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Beige400)
                    .weight(1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_photo),
                    contentDescription = stringResource(R.string.upload_photo_btn_desc),
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.padding(end = 24.dp))
        }
        Spacer(modifier = Modifier.padding(top = 24.dp))

        if (photoSelected) {
            // todo: 그리드 목록

        }

        Spacer(modifier = Modifier.weight(1f))
        LoveMarkerButton(
            onClick = { /*TODO*/ },
            buttonText = stringResource(R.string.upload_photo_next_btn_text),
            enabled = photoSelected,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PhotoPreview() {
    LoveMarkerTheme {
        PhotoScreen(
            navigateUp = {},
            photoSelected = false
        )
    }
}
