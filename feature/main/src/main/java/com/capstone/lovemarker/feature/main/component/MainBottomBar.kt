package com.capstone.lovemarker.feature.main.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import com.capstone.lovemarker.feature.main.navigation.MainTab
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun MainBottomBar(
    visible: Boolean,
    tabs: PersistentList<MainTab>,
    currentTab: MainTab?,
    onTabSelected: (MainTab) -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideIn { IntOffset(0, it.height) },
        exit = fadeOut() + slideOut { IntOffset(0, it.height) }
    ) {
        NavigationBar(
            containerColor = LoveMarkerTheme.colorScheme.surfaceContainer
        ) {
            tabs.forEachIndexed { idx, tab ->
                MainBottomBarItem(
                    tab = tab,
                    selected = tab == currentTab,
                    onClick = { onTabSelected(tab) }
                )
            }
        }
    }
}

@Composable
private fun RowScope.MainBottomBarItem(
    tab: MainTab,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(
                painter = painterResource(id = tab.iconResId),
                contentDescription = tab.labelText
            )
        },
        label = {
            Text(
                text = tab.labelText,
                style = if (selected) {
                    LoveMarkerTheme.typography.label12B
                } else {
                    LoveMarkerTheme.typography.label12M
                }
            )
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = LoveMarkerTheme.colorScheme.onSurface800,
            selectedTextColor = LoveMarkerTheme.colorScheme.onSurface800,
            indicatorColor = LoveMarkerTheme.colorScheme.surfaceVariant,
            unselectedIconColor = LoveMarkerTheme.colorScheme.onSurface600,
            unselectedTextColor = LoveMarkerTheme.colorScheme.onSurface600,
        )
    )
}

@Preview
@Composable
private fun MainBottomBarPreview() {
    LoveMarkerTheme {
        MainBottomBar(
            visible = true,
            tabs = MainTab.entries.toPersistentList(),
            currentTab = MainTab.MAP,
            onTabSelected = {}
        )
    }
}