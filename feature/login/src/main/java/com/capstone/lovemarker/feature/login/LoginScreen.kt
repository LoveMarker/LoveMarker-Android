package com.capstone.lovemarker.feature.login

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import com.capstone.lovemarker.feature.login.di.OAuthEntryPoint
import com.capstone.lovemarker.domain.oauth.service.OAuthService
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun LoginRoute(
    navigateToNickname: () -> Unit,
    navigateToMap: () -> Unit,
    showErrorSnackbar: (Throwable?) -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val googleAuthService = rememberGoogleAuthService() ?: return

    LaunchedEffect(viewModel.loginSideEffect, lifecycleOwner) {
        viewModel.loginSideEffect
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { sideEffect ->
                when (sideEffect) {
                    is LoginSideEffect.LoginSuccess -> {
                        if (sideEffect.isRegistered) {
                            navigateToMap()
                        } else {
                            navigateToNickname()
                        }
                    }

                    is LoginSideEffect.ShowErrorSnackbar -> {
                        showErrorSnackbar(sideEffect.throwable)
                    }
                }
            }
    }

    LoginScreen(
        onLoginButtonClick = {
            lifecycleOwner.lifecycleScope.launch {
                runCatching {
                    googleAuthService.signIn()
                }.onSuccess { oauthToken ->
                    viewModel.postLogin(socialToken = oauthToken.accessToken)
                }.onFailure {
                    showErrorSnackbar(it)
                }
            }
        }
    )
}

@Composable
private fun rememberGoogleAuthService(): OAuthService? {
    val activityContext = LocalContext.current as? ComponentActivity
    return runCatching {
        requireNotNull(activityContext) { "Activity context is required for getting OAuthService" }
        val entryPoint = EntryPointAccessors.fromActivity<OAuthEntryPoint>(activityContext)
        entryPoint.googleAuthService()
    }.getOrElse { throwable ->
        Timber.e(throwable.message)
        null
    }
}

@Composable
fun LoginScreen(
    onLoginButtonClick: () -> Unit,
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
                painter = painterResource(id = R.drawable.img_login_couple),
                contentDescription = stringResource(R.string.login_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { onLoginButtonClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 50.dp, end = 50.dp, bottom = 74.dp)
                    .heightIn(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 2.dp
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_login_google_logo),
                        contentDescription = stringResource(id = R.string.login_btn_text),
                        modifier = Modifier.padding(end = 10.dp)
                    )
                    Text(
                        text = stringResource(R.string.login_btn_text)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginPreview() {
    LoveMarkerTheme {
        LoginScreen(
            onLoginButtonClick = { }
        )
    }
}