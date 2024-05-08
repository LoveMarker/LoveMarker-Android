package com.capstone.lovemarker

import android.os.Build
import android.os.Bundle
import android.os.ext.SdkExtensions.getExtensionVersion
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            if (isPhotoPickerAvailable()) {
                launchPhotoPicker()
            } else {
                launchGallery()
            }
        }
    }

    private fun isPhotoPickerAvailable(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            true
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getExtensionVersion(Build.VERSION_CODES.R) >= 2
        } else {
            false
        }
    }

    private fun launchPhotoPicker() {
        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {

                } else {

                }
            }

        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun launchGallery() {
        val launcher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
                if (imageUri != null) {

                }
            }

        launcher.launch("image/*")
    }
}
