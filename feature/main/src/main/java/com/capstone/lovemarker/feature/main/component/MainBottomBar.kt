package com.capstone.lovemarker.feature.main.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import com.capstone.lovemarker.feature.main.navigation.MainTab
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import timber.log.Timber

@Composable
fun MainBottomBar(
    visible: Boolean,
    tabs: PersistentList<MainTab>,
    currentTab: MainTab?,
    onTabSelected: (MainTab) -> Unit,
) {
//    Timber.tag("recomposition").d("Main bottom bar")

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideIn { IntOffset(0, it.height) },
        exit = fadeOut() + slideOut { IntOffset(0, it.height) }
    ) {
        NavigationBar(
            containerColor = LoveMarkerTheme.colorScheme.surfaceContainer
        ) {
            tabs.forEach { tab ->
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
                    LoveMarkerTheme.typography.label11B
                } else {
                    LoveMarkerTheme.typography.label11M
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