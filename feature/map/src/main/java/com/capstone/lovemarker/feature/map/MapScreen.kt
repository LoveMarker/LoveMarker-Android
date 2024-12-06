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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.capstone.lovemarker.core.common.extension.dropShadow
import com.capstone.lovemarker.core.designsystem.component.dialog.NotFullSizeDialog
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import com.capstone.lovemarker.core.designsystem.theme.Red200
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@Composable
fun MapRoute(
    innerPadding: PaddingValues,
    navigateToPhoto: () -> Unit,
    navigateToMatching: () -> Unit,
//    viewModel: MapViewModel = hiltViewModel()
) {
    Timber.tag("recomposition").d("Map Route")

//    val state by viewModel.state.collectAsStateWithLifecycle()
//    val cameraPositionState = rememberCameraPositionState()

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    RequestLocationPermission(
        context = context,
        onPermissionGranted = {
//            viewModel.initCurrentLocation(fusedLocationClient)
        }
    )

//    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
//        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
//            .collectLatest { sideEffect ->
//                when (sideEffect) {
//                    is MapSideEffect.NavigateToPhoto -> {
//                        navigateToPhoto()
//                    }
//
//                    is MapSideEffect.NavigateToMatching -> {
//                        navigateToMatching()
//                    }
//                }
//            }
//    }

    Button(onClick = {
        navigateToPhoto()
    }) {
        Text("navigate to photo")
    }

//    MapScreen(
//        innerPadding = innerPadding,
//        currentLocation = state.currentLocation,
//        cameraPositionState = cameraPositionState,
//        onUploadButtonClick = viewModel::triggerPhotoNavigationEffect
//    )
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
    currentLocation: LatLng?,
    cameraPositionState: CameraPositionState,
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
            currentLocation?.let { position ->
                cameraPositionState.position = CameraPosition.fromLatLngZoom(position, 18f)
                CurrentLocationMarker(position)
            }
        }
        MapGuideHeader(
            modifier = Modifier.align(Alignment.TopCenter)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 24.dp)
        ) {
            MoveCurrentLocationButton(
                modifier = Modifier
                    .dropShadow(
                        shape = CircleShape,
                        blur = 10.dp,
                        offsetY = 3.dp
                    )
                    .clip(CircleShape)
                    .align(Alignment.Start)
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
fun MemoryUploadButton(
    onUploadButtonClick: () -> Unit,
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

@Composable
fun MoveCurrentLocationButton(
    modifier: Modifier
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_btn_location),
        contentDescription = stringResource(R.string.map_location_btn_desc),
        tint = Color.Unspecified,
        modifier = modifier
    )
}

@Composable
fun MapGuideHeader(modifier: Modifier) {
    Column(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(24.dp))
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
}

@Composable
fun CurrentLocationMarker(
    position: LatLng
) {
    MarkerComposable(
        state = rememberMarkerState(position = position),
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

@Preview(showBackground = true)
@Composable
private fun MapPreview() {
    LoveMarkerTheme {
//        MapScreen(
//            onUploadButtonClick = {}
//        )
    }
}
