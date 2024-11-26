package com.capstone.lovemarker.feature.map

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.lovemarker.core.common.extension.dropShadow
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import com.capstone.lovemarker.core.designsystem.theme.Red200
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import timber.log.Timber

@Composable
fun MapRoute(
    innerPadding: PaddingValues,
    navigateToPhoto: () -> Unit,
    showErrorSnackbar: (Throwable?) -> Unit,
    viewModel: MapViewModel = viewModel()
) {
    val mapState by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraPositionState = rememberCameraPositionState()
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    RequestLocationPermission(
        context = context,
        onPermissionGranted = {
            viewModel.moveCurrentLocation(fusedLocationClient)
        },
        onPermissionDenied = {
            viewModel.moveDefaultLocation()
        }
    )

    MapScreen(
        innerPadding = innerPadding,
        cameraPositionState = cameraPositionState,
        currentLocation = mapState.currentLocation,
        onCurrentLocationButtonClick = {
            viewModel.moveCurrentLocation(fusedLocationClient)
        },
        onUploadButtonClick = navigateToPhoto
    )
}

@Composable
fun RequestLocationPermission(
    context: Context,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
) {
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            Timber.d("step 3: $isGranted")
            if (isGranted) {
                onPermissionGranted()
            } else {
                onPermissionDenied()
            }
        }
    )

    LaunchedEffect(Unit) {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                Timber.d("step 1")
                onPermissionGranted()
            }

            else -> {
                Timber.d("step 2")
                permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }
}

@Composable
fun MapScreen(
    innerPadding: PaddingValues,
    cameraPositionState: CameraPositionState,
    currentLocation: LatLng?,
    onCurrentLocationButtonClick: () -> Unit,
    onUploadButtonClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        if (currentLocation != null) {
            GoogleMap(
                cameraPositionState = cameraPositionState
            ) {
                cameraPositionState.position = CameraPosition.fromLatLngZoom(currentLocation, 18f)
                CurrentLocationMarker(
                    currentLocation = currentLocation
                )
            }
        }
        Column(
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            MapHeader()
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 24.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_btn_location),
                contentDescription = stringResource(R.string.map_location_btn_desc),
                tint = Color.Unspecified,
                modifier = Modifier
                    .dropShadow(
                        shape = CircleShape,
                        blur = 10.dp,
                        offsetY = 3.dp
                    )
                    .clip(CircleShape)
                    .align(Alignment.Start)
                    .clickable {
                        onCurrentLocationButtonClick()
                    }
            )
            Spacer(modifier = Modifier.padding(14.dp))
            MemoryUploadButton(
                onUploadButtonClick = onUploadButtonClick
            )
            Spacer(modifier = Modifier.padding(28.dp))
        }
    }
}

@Composable
fun MapHeader() {
    Box(
        modifier = Modifier
            .dropShadow(
                shape = RoundedCornerShape(30.dp),
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
            text = stringResource(R.string.map_guide_title),
            style = LoveMarkerTheme.typography.label13M,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 48.dp, end = 26.dp, top = 11.dp, bottom = 11.dp)
        )
    }
}

@Composable
fun CurrentLocationMarker(
    currentLocation: LatLng
) {
    val markerState = rememberMarkerState()
    LaunchedEffect(currentLocation) {
        markerState.position = currentLocation
    }

    MarkerComposable(
        state = markerState,
        title = "Your Location",
        snippet = "This is where you are currently located."
    ) {
        Box {
            Icon(
                painter = painterResource(id = R.drawable.ic_marker_area),
                contentDescription = stringResource(R.string.map_location_marker_desc),
                tint = Color.Unspecified,
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_marker_pin),
                contentDescription = stringResource(R.string.map_location_marker_desc),
                tint = Color.Unspecified,
                modifier = Modifier
                    .dropShadow(
                        shape = CircleShape,
                    )
                    .clip(CircleShape)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun MemoryUploadButton(
    onUploadButtonClick: () -> Unit
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
}

@Preview(showBackground = true)
@Composable
private fun MapPreview() {
    LoveMarkerTheme {
//        MapScreen(
//            onUploadButtonClick = {}
//        )
    }
}
