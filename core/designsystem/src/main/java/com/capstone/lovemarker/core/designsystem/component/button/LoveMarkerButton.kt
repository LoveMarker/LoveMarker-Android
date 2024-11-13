package com.capstone.lovemarker.core.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.capstone.lovemarker.core.common.extension.noRippleClickable
import com.capstone.lovemarker.core.designsystem.theme.Brown100
import com.capstone.lovemarker.core.designsystem.theme.Gray200
import com.capstone.lovemarker.core.designsystem.theme.Gray500
import com.capstone.lovemarker.core.designsystem.theme.Gray800
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme

@Composable
fun LoveMarkerButton(
    onClick: () -> Unit,
    buttonText: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .run {
                if (enabled) noRippleClickable(onClick = onClick) else this
            }
            .fillMaxWidth()
            .height(74.dp)
            .padding(horizontal = 16.dp, vertical = 14.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = if (enabled) Brown100 else Gray200),
    ) {
        Text(
            text = buttonText,
            style = LoveMarkerTheme.typography.body16M,
            color = if (enabled) Gray800 else Gray500
        )
    }
}

