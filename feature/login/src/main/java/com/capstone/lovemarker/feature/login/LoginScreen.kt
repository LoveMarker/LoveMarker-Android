package com.capstone.lovemarker.feature.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
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
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginRoute(
    navigateToNickname: () -> Unit,
    showErrorSnackbar: (Throwable?) -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel.loginSideEffect, lifecycleOwner) {
        viewModel.loginSideEffect
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { sideEffect ->
                when (sideEffect) {
                    is LoginSideEffect.NavigateToNickname -> {
                        //navigateToNickname()
                    }

                    is LoginSideEffect.ShowErrorSnackbar -> {
                        showErrorSnackbar(sideEffect.throwable)
                    }
                }
            }
    }

    LoginScreen(
        onLoginButtonClick = {
            viewModel.signIn()
        }
    )
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
                text = stringResource(id = R.string.app_name),
                fontFamily = FontFamily(Font(R.font.ribeye_regular)),
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
                    .padding(start = 48.dp, end = 48.dp, bottom = 74.dp)
                    .heightIn(42.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 2.dp
                )
            ) {
                Text(text = "Sign in with Google")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginPreview() {
    LoveMarkerTheme {

    }
}