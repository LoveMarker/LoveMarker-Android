package com.capstone.lovemarker.feature.mypage

import androidx.activity.ComponentActivity
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
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
import com.capstone.lovemarker.domain.oauth.service.OAuthService
import com.capstone.lovemarker.feature.mypage.di.OAuthEntryPoint
import com.jakewharton.processphoenix.ProcessPhoenix
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun MyPageRoute(
    innerPadding: PaddingValues,
    navigateToMatching: () -> Unit,
    navigateToNickname: (String) -> Unit,
    navigateToMyFeed: () -> Unit,
    showErrorSnackbar: (Throwable?) -> Unit,
    modifiedNickname: String? = null,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val googleAuthService = rememberGoogleAuthService() ?: return

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

                    is MyPageSideEffect.NavigateToMyFeed -> {
                        navigateToMyFeed()
                    }

                    is MyPageSideEffect.RestartApp -> {
                        ProcessPhoenix.triggerRebirth(context)
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
        showCoupleDisconnectDialog = state.showDisconnectDialog,
        onCoupleDisconnectClick = {
            viewModel.updateDisconnectDialogState(true)
        },
        onCoupleDisconnectConfirm = {
            viewModel.updateDisconnectDialogState(false)
            viewModel.deleteCouple()
        },
        onCoupleDisconnectDismiss = {
            viewModel.updateDisconnectDialogState(false)
        },
        onCoupleMatchingClick = viewModel::triggerMatchingNavigationEffect,
        onNicknameModifyClick = viewModel::triggerNicknameNavigationEffect,
        onMyFeedClick = viewModel::triggerMyFeedNavigationEffect,
        showLogoutDialog = state.showLogoutDialog,
        onLogoutClick = {
            viewModel.updateLogoutDialogState(true)
        },
        onLogoutConfirm = {
            viewModel.updateDisconnectDialogState(false)

            lifecycleOwner.lifecycleScope.launch {
                runCatching {
                    googleAuthService.signOut()
                }.onSuccess {
                    viewModel.apply {
                        clearUserDataStore()
                        triggerRestartAppEffect()
                    }
                }.onFailure {
                    showErrorSnackbar(it)
                }
            }
        },
        onLogoutDismiss = {
            viewModel.updateLogoutDialogState(false)
        },
    )
}

@Composable
private fun rememberGoogleAuthService(): OAuthService? {
    val activityContext = LocalContext.current as? ComponentActivity
    return runCatching {
        requireNotNull(activityContext) { "Activity context is required to get OAuthService" }
        val entryPoint = EntryPointAccessors.fromActivity<OAuthEntryPoint>(activityContext)
        entryPoint.googleAuthService()
    }.getOrElse { throwable ->
        Timber.e(throwable.message)
        null
    }
}

@Composable
fun MyPageScreen(
    innerPadding: PaddingValues,
    nickname: String,
    anniversaryDays: Int,
    coupleConnected: Boolean,
    partnerNickname: String,
    showCoupleDisconnectDialog: Boolean,
    onCoupleDisconnectClick: () -> Unit,
    onCoupleDisconnectConfirm: () -> Unit,
    onCoupleDisconnectDismiss: () -> Unit,
    onCoupleMatchingClick: () -> Unit,
    onNicknameModifyClick: () -> Unit,
    onMyFeedClick: () -> Unit,
    showLogoutDialog: Boolean,
    onLogoutClick: () -> Unit,
    onLogoutConfirm: () -> Unit,
    onLogoutDismiss: () -> Unit,
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
            onMatchingButtonClick = onCoupleMatchingClick
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .background(Gray50)
                .padding(top = 8.dp)
        )
        SettingSection(
            onCoupleDisconnectClick = onCoupleDisconnectClick,
            onNicknameModifyClick = onNicknameModifyClick,
            onMyFeedClick = onMyFeedClick,
            onLogoutClick = onLogoutClick,

            )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "탈퇴하기",
            style = LoveMarkerTheme.typography.body14M,
            color = Gray400,
            modifier = Modifier.padding(horizontal = 22.dp, vertical = 16.dp)
        )
    }

    if (showCoupleDisconnectDialog) {
        CoupleDisconnectDialog(
            coupleConnected = coupleConnected,
            onConfirmButtonClick = onCoupleDisconnectConfirm,
            onDismissButtonClick = onCoupleDisconnectDismiss,
        )
    }

    if (showLogoutDialog) {
        LogoutDialog(
            onConfirmButtonClick = onLogoutConfirm,
            onDismissButtonClick = onLogoutDismiss,
        )
    }
}

@Composable
fun CoupleDisconnectDialog(
    coupleConnected: Boolean,
    onConfirmButtonClick: () -> Unit,
    onDismissButtonClick: () -> Unit,
) {
    if (coupleConnected) {
        DoubleButtonDialog(
            title = stringResource(R.string.mypage_disconnect_dialog_title),
            description = "",
            confirmButtonText = stringResource(R.string.mypage_dialog_confirm_text),
            dismissButtonText = stringResource(R.string.mypage_dialog_dimiss_text),
            onConfirmButtonClick = onConfirmButtonClick,
            onDismissButtonClick = onDismissButtonClick,
        )
    } else {
        SingleButtonDialog(
            title = stringResource(R.string.mypage_already_disconnect_dialog_title),
            description = "",
            buttonText = stringResource(R.string.mypage_dialog_confirm_text),
            onClick = onDismissButtonClick
        )
    }
}

@Composable
fun LogoutDialog(
    onConfirmButtonClick: () -> Unit,
    onDismissButtonClick: () -> Unit,
) {
    DoubleButtonDialog(
        title = stringResource(R.string.mypage_logout_dialog_title),
        description = "",
        confirmButtonText = stringResource(R.string.mypage_dialog_confirm_text),
        dismissButtonText = stringResource(R.string.mypage_dialog_dimiss_text),
        onConfirmButtonClick = onConfirmButtonClick,
        onDismissButtonClick = onDismissButtonClick,
    )
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
        NicknameSection(
            nickname = nickname,
            coupleConnected = coupleConnected,
            partnerNickname = partnerNickname,
            onMatchingButtonClick = onMatchingButtonClick
        )
        Image(
            painter = painterResource(id = R.drawable.img_mypage_couple),
            contentDescription = "couple image",
            modifier = Modifier.padding(start = 24.dp, top = 26.dp)
        )
    }
}

@Composable
fun NicknameSection(
    nickname: String,
    coupleConnected: Boolean,
    partnerNickname: String,
    onMatchingButtonClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 26.dp)
    ) {
        Text(
            text = nickname,
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
}

@Composable
fun SettingSection(
    onCoupleDisconnectClick: () -> Unit,
    onNicknameModifyClick: () -> Unit,
    onMyFeedClick: () -> Unit,
    onLogoutClick: () -> Unit,
) {
    Column {
        SettingItem(
            title = stringResource(R.string.mypage_nickname_btn_title),
            onItemClick = onNicknameModifyClick
        )
        SettingItem(
            title = stringResource(R.string.mypage_myfeed_btn_title),
            onItemClick = onMyFeedClick
        )
        SettingItem(
            title = stringResource(R.string.mypage_couple_disconnect_btn_title),
            onItemClick = onCoupleDisconnectClick
        )
        SettingItem(
            title = stringResource(R.string.mypage_logout_btn_title),
            onItemClick = onLogoutClick
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
            navigateToMyFeed = {},
            showErrorSnackbar = {}
        )
    }
}
