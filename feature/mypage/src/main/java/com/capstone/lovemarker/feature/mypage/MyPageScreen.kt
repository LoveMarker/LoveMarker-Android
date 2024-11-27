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
import androidx.compose.material3.Surface
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
import com.capstone.lovemarker.core.designsystem.theme.Gray200
import com.capstone.lovemarker.core.designsystem.theme.Gray300
import com.capstone.lovemarker.core.designsystem.theme.Gray400
import com.capstone.lovemarker.core.designsystem.theme.Gray50
import com.capstone.lovemarker.core.designsystem.theme.Gray500
import com.capstone.lovemarker.core.designsystem.theme.Gray700
import com.capstone.lovemarker.core.designsystem.theme.Gray800
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import com.capstone.lovemarker.core.designsystem.theme.Red500
import com.capstone.lovemarker.core.designsystem.theme.White
import com.capstone.lovemarker.feature.mypage.model.CoupleModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MyPageRoute(
    innerPadding: PaddingValues,
    navigateToMatching: () -> Unit,
    navigateToNickname: () -> Unit,
    showErrorSnackbar: (Throwable?) -> Unit,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { sideEffect ->
                when (sideEffect) {
                    is MyPageSideEffect.NavigateToMatching -> {
                        navigateToMatching() // todo: 커플 연결되면 UI 갱신
                    }

                    is MyPageSideEffect.NavigateToNickname -> {
                        navigateToNickname() // todo: 변경된 닉네임으로 갱신
                    }

                    is MyPageSideEffect.ShowErrorSnackbar -> {
                        showErrorSnackbar(sideEffect.throwable)
                    }
                }
            }
    }

    MaPageScreen(
        innerPadding = innerPadding,
        nickname = state.nickname,
        coupleModel = state.coupleModel,
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
        onMatchingButtonClick = navigateToMatching,
        onNicknameButtonClick = navigateToNickname
    )
}

@Composable
fun MaPageScreen(
    innerPadding: PaddingValues,
    nickname: String,
    coupleModel: CoupleModel,
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
            coupleModel = coupleModel,
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
        Surface(
            color = Gray500.copy(alpha = 0.25f),
            modifier = Modifier.fillMaxSize()
        ) {
            DoubleButtonDialog(
                title = "커플 연결을 해제하시겠어요?",
                description = "",
                confirmButtonText = "확인",
                dismissButtonText = "취소",
                onConfirmButtonClick = onConfirmButtonClick,
                onDismissButtonClick = onDismissButtonClick,
            )
        }
    }
}

@Composable
fun CoupleSection(
    nickname: String,
    coupleModel: CoupleModel,
    onMatchingButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier.background(White)
    ) {
        Spacer(modifier = Modifier.padding(top = 30.dp))
        Text(
            text = "사랑한지 ${coupleModel.anniversaryDays}일째",
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
                colorFilter = if (coupleModel.connected) ColorFilter.tint(Red500)
                else ColorFilter.tint(Gray300)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = if (coupleModel.connected) coupleModel.partnerNickname
                    else stringResource(R.string.mypage_anonymous_partner_nickname),
                    style = LoveMarkerTheme.typography.headline18M,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 30.dp),
                    color = if (coupleModel.connected) Gray800 else Gray400
                )
                if (!coupleModel.connected) {
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
