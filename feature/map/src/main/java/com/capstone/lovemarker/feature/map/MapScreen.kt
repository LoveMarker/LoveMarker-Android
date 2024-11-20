package com.capstone.lovemarker.feature.map

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BlurMaskFilter
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.lovemarker.core.designsystem.theme.Black
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import com.capstone.lovemarker.core.designsystem.theme.Red200
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import timber.log.Timber

@Composable
fun MapRoute(
    innerPadding: PaddingValues,
    viewModel: MapViewModel = viewModel()
) {
    val cameraPositionState = rememberCameraPositionState()
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val userLocation by viewModel.userLocation

    RequestLocationPermission(
        context = context,
        onPermissionGranted = {
            viewModel.getUserLocation(fusedLocationClient)
        }
    )

    MapScreen(
        innerPadding = innerPadding,
        cameraPositionState = cameraPositionState,
        userLocation = userLocation,
        onUploadButtonClick = { /* TODO */ }
    )
}

@Composable
fun RequestLocationPermission(
    context: Context,
    onPermissionGranted: () -> Unit,
) {
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onPermissionGranted()
        } else {
            Timber.e("Location permission was denied by the user.")
        }
    }

    LaunchedEffect(Unit) {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                onPermissionGranted()
            }

            else -> {
                permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }
}

@Composable
fun MapScreen(
    innerPadding: PaddingValues,
    cameraPositionState: CameraPositionState,
    userLocation: LatLng?,
    onUploadButtonClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        GoogleMap(
            cameraPositionState = cameraPositionState
        ) {
            userLocation?.let { position ->
                Marker(
                    state = MarkerState(position = position),
                    title = "Your Location",
                    snippet = "This is where you are currently located."
                )
                cameraPositionState.position = CameraPosition.fromLatLngZoom(position, 18f)
            }
        }
        Column(
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .dropShadow(
                        shape = RoundedCornerShape(30.dp),
                        blur = 10.dp,
                        offsetY = 2.dp,
                    )
                    .clip(RoundedCornerShape(30.dp))
                    .background(color = Color.White)

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_app_logo),
                    contentDescription = stringResource(R.string.map_logo_icon_desc),
                    tint = Color.Unspecified,
                    modifier = Modifier.align(Alignment.TopStart)
                )
                Text(
                    text = "연인 간의 추억을 지도에 기록해 보세요!",
                    style = LoveMarkerTheme.typography.label13M,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(start = 48.dp, end = 26.dp, top = 11.dp, bottom = 11.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .dropShadow(
                        shape = RoundedCornerShape(12.dp),
                        offsetY = 3.dp
                    )
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(color = Red200)
                    .clickable {
                        onUploadButtonClick()
                    }
            ) {
                Text(
                    text = stringResource(R.string.map_upload_btn_text),
                    style = LoveMarkerTheme.typography.label13M,
                    modifier = Modifier.padding(horizontal = 96.dp, vertical = 14.dp)
                )
            }
            Spacer(modifier = Modifier.padding(28.dp))
        }

    }
}

fun Modifier.dropShadow(
    shape: Shape,
    color: Color = Color.Black.copy(0.25f),
    blur: Dp = 4.dp,
    offsetY: Dp = 4.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0.dp
) = this.drawBehind {
    val shadowSize = Size(size.width + spread.toPx(), size.height + spread.toPx())
    val shadowOutline = shape.createOutline(shadowSize, layoutDirection, this)

    val paint = Paint().apply {
        this.color = color
    }

    if (blur.toPx() > 0) {
        paint.asFrameworkPaint().apply {
            maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
        }
    }

    drawIntoCanvas { canvas ->
        canvas.save()
        canvas.translate(offsetX.toPx(), offsetY.toPx())
        canvas.drawOutline(shadowOutline, paint)
        canvas.restore()
    }
}

@Preview(showBackground = true)
@Composable
private fun MapPreview() {
    LoveMarkerTheme {

    }
}
