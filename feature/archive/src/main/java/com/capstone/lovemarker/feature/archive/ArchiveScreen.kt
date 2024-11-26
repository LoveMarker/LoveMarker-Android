package com.capstone.lovemarker.feature.archive

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.lovemarker.core.common.extension.dropShadow
import com.capstone.lovemarker.core.designsystem.theme.Beige400
import com.capstone.lovemarker.core.designsystem.theme.Gray200
import com.capstone.lovemarker.core.designsystem.theme.Gray300
import com.capstone.lovemarker.core.designsystem.theme.Gray700
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import com.capstone.lovemarker.core.designsystem.theme.White

@Composable
fun ArchiveRoute(
    innerPadding: PaddingValues,
    navigateToDetail: (Int) -> Unit,
) {
    ArchiveScreen(
//        innerPadding = innerPadding,
        onItemClick = navigateToDetail
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchiveScreen(
//    innerPadding: PaddingValues,
    onItemClick: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = com.capstone.lovemarker.core.designsystem.R.string.app_name),
                    fontFamily = FontFamily(Font(resId = com.capstone.lovemarker.core.designsystem.R.font.ribeye_regular)),
                    fontSize = 16.sp
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = White
            )
        )
        HorizontalDivider(
            color = Gray200
        )
        if (fakeItems.isEmpty()) {
            EmptyArchive()
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(16.dp),
            ) {
                items(fakeItems) { item ->
                    ArchiveItem(item)
                }
            }
        }
    }
}

@Composable
fun EmptyArchive() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.img_archive_empty),
            contentDescription = stringResource(R.string.archive_empty_image_desc),
            tint = Color.Unspecified
        )
        Text(
            text = stringResource(R.string.archive_empty_guide_text),
            style = LoveMarkerTheme.typography.body14M,
            color = Gray700,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 72.dp)
        )
    }
}

data class Item(
    val title: String,
    val address: String,
    val date: String,
)

val emptyList = listOf<Item>()

val fakeItems = listOf(
    Item(
        title = "경주 여행",
        address = "경주시 경주",
        date = "2024-10-11"
    ),
    Item(
        title = "경주 여행",
        address = "경주시 경주",
        date = "2024-10-11"
    ),
    Item(
        title = "경주 여행",
        address = "경주시 경주",
        date = "2024-10-11"
    ),
)

@Composable
fun ArchiveItem(
    item: Item
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .dropShadow(
                shape = RoundedCornerShape(6.dp),
                offsetY = 2.dp,
                blur = 2.dp
            )
            .clip(RoundedCornerShape(6.dp))
            .background(White)
    ) {
        Spacer(modifier = Modifier.padding(start = 16.dp))
        Image(
            painter = painterResource(R.drawable.img_test),
            contentDescription = null,
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(6.dp))
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 14.dp, bottom = 14.dp, end = 16.dp)
        ) {
            Text(
                text = item.title,
                style = LoveMarkerTheme.typography.body16B,
            )
            Text(
                text = item.address,
                style = LoveMarkerTheme.typography.body14M,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = item.date,
                style = LoveMarkerTheme.typography.label13M,
                modifier = Modifier
                    .align(Alignment.End)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ArchivePreview() {
    LoveMarkerTheme {
        ArchiveScreen(
            onItemClick = {}
        )
    }
}
