package com.capstone.lovemarker.feature.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.capstone.lovemarker.core.common.extension.noRippleClickable
import com.capstone.lovemarker.core.designsystem.component.appbar.LoveMarkerTopAppBar
import com.capstone.lovemarker.core.designsystem.component.dialog.DoubleButtonDialog
import com.capstone.lovemarker.core.designsystem.component.dialog.SingleButtonDialog
import com.capstone.lovemarker.core.designsystem.theme.Gray200
import com.capstone.lovemarker.core.designsystem.theme.Gray300
import com.capstone.lovemarker.core.designsystem.theme.Gray400
import com.capstone.lovemarker.core.designsystem.theme.Gray50
import com.capstone.lovemarker.core.designsystem.theme.Gray700
import com.capstone.lovemarker.core.designsystem.theme.Gray800
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import com.capstone.lovemarker.core.designsystem.theme.Red500
import com.capstone.lovemarker.core.designsystem.theme.White
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@Composable
fun MyPageRoute(
    innerPadding: PaddingValues,
    navigateToMatching: () -> Unit,
    navigateToNickname: (String) -> Unit,
    showErrorSnackbar: (Throwable?) -> Unit,
    modifiedNickname: String? = null,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getMyPageInfo()
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { sideEffect ->
                when (sideEffect) {
                    is MyPageSideEffect.NavigateToMatching -> {
                        navigateToMatching()
                    }

                    is MyPageSideEffect.NavigateToNickname -> {
                        navigateToNickname(sideEffect.nickname)
                    }

                    is MyPageSideEffect.ShowErrorSnackbar -> {
                        showErrorSnackbar(sideEffect.throwable)
                    }
                }
            }
    }

    MyPageScreen(
        innerPadding = innerPadding,
        nickname = modifiedNickname ?: state.nickname,
        anniversaryDays = state.anniversaryDays,
        coupleConnected = state.coupleConnected,
        partnerNickname = state.partnerNickname,
        showDisconnectDialog = state.showDisconnectDialog,
        onDisconnectButtonClick = {
            viewModel.updateDisconnectDialogState(true)
        },
        onConfirmButtonClick = {
            viewModel.updateDisconnectDialogState(false)
            viewModel.deleteCouple()
        },
        onDismissButtonClick = {
            viewModel.updateDisconnectDialogState(false)
        },
        onMatchingButtonClick = viewModel::triggerMatchingNavigationEffect,
        onNicknameButtonClick = viewModel::triggerNicknameNavigationEffect
    )
}

@Composable
fun MyPageScreen(
    innerPadding: PaddingValues,
    nickname: String,
    anniversaryDays: Int,
    coupleConnected: Boolean,
    partnerNickname: String,
    showDisconnectDialog: Boolean,
    onDisconnectButtonClick: () -> Unit,
    onConfirmButtonClick: () -> Unit,
    onDismissButtonClick: () -> Unit,
    onMatchingButtonClick: () -> Unit,
    onNicknameButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray50)
            .padding(innerPadding)
    ) {
        LoveMarkerTopAppBar()
        HorizontalDivider(
            color = Gray200
        )
        CoupleSection(
            nickname = nickname,
            anniversaryDays = anniversaryDays,
            coupleConnected = coupleConnected,
            partnerNickname = partnerNickname,
            onMatchingButtonClick = onMatchingButtonClick
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .background(Gray50)
                .padding(top = 8.dp)
        )
        SettingSection(
            onNicknameButtonClick = onNicknameButtonClick,
            onDisconnectButtonClick = onDisconnectButtonClick
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "탈퇴하기",
            style = LoveMarkerTheme.typography.body16M,
            color = Gray400,
            modifier = Modifier.padding(horizontal = 22.dp, vertical = 16.dp)
        )
    }

    if (showDisconnectDialog) {
        if (coupleConnected) {
            DoubleButtonDialog(
                title = stringResource(R.string.mypage_disconnect_dialog_title),
                description = "",
                confirmButtonText = stringResource(R.string.mypage_disconnect_dialog_confirm_text),
                dismissButtonText = stringResource(R.string.mypage_disconnect_dialog_dimiss_text),
                onConfirmButtonClick = onConfirmButtonClick,
                onDismissButtonClick = onDismissButtonClick,
            )
        } else {
            SingleButtonDialog(
                title = stringResource(R.string.mypage_already_disconnect_dialog_title),
                description = "",
                buttonText = stringResource(R.string.mypage_disconnect_dialog_confirm_text),
                onConfirmButtonClick = onDismissButtonClick
            )
        }
    }
}

@Composable
fun CoupleSection(
    nickname: String,
    anniversaryDays: Int,
    coupleConnected: Boolean,
    partnerNickname: String,
    onMatchingButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier.background(White)
    ) {
        Spacer(modifier = Modifier.padding(top = 30.dp))
        Text(
            text = "사랑한지 ${anniversaryDays}일째",
            style = LoveMarkerTheme.typography.body16M,
            textAlign = TextAlign.Center,
            color = Gray800,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 26.dp)
        ) {
            Text(
                text = nickname, // todo: 닉네임 길이 제한 필요 (한 줄 넘지 않게)
                style = LoveMarkerTheme.typography.headline18M,
                textAlign = TextAlign.End,
                color = Gray800,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 30.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.img_mypage_heart),
                contentDescription = "heart image",
                colorFilter = if (coupleConnected) ColorFilter.tint(Red500)
                else ColorFilter.tint(Gray300)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = if (coupleConnected) partnerNickname
                    else stringResource(R.string.mypage_anonymous_partner_nickname),
                    style = LoveMarkerTheme.typography.headline18M,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 30.dp),
                    color = if (coupleConnected) Gray800 else Gray400
                )
                if (!coupleConnected) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_mypage_setting_nav),
                        contentDescription = "icon for navigate to nickname",
                        tint = Gray300,
                        modifier = Modifier
                            .padding(end = 14.dp)
                            .noRippleClickable {
                                onMatchingButtonClick()
                            }
                    )
                }
            }

        }
        Image(
            painter = painterResource(id = R.drawable.img_mypage_couple),
            contentDescription = "couple image",
            modifier = Modifier.padding(start = 24.dp, top = 26.dp)
        )
    }
}

@Composable
fun SettingSection(
    onNicknameButtonClick: () -> Unit,
    onDisconnectButtonClick: () -> Unit,
) {
    Column {
        SettingItem(
            title = "닉네임 변경",
            onItemClick = onNicknameButtonClick
        )
        SettingItem(
            title = "내가 올린 추억",
            onItemClick = {}
        )
        SettingItem(
            title = "커플 연결 해제",
            onItemClick = onDisconnectButtonClick
        )
        SettingItem(
            title = "로그아웃",
            onItemClick = {}
        )
    }
}

@Composable
fun SettingItem(
    title: String,
    onItemClick: () -> Unit,
) {
    Column(
        modifier = Modifier.noRippleClickable {
            onItemClick()
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .padding(start = 22.dp, top = 16.dp, bottom = 16.dp, end = 14.dp)
        ) {
            Text(
                text = title,
                style = LoveMarkerTheme.typography.body16M,
                color = Gray700,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_mypage_setting_nav),
                contentDescription = "icon for navigate to nickname",
                tint = Gray300
            )
        }
        HorizontalDivider(
            color = Gray50,
            thickness = 4.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPagePreview() {
    LoveMarkerTheme {
        MyPageRoute(
            innerPadding = PaddingValues(),
            navigateToMatching = {},
            navigateToNickname = {},
            showErrorSnackbar = {}
        )
    }
}
