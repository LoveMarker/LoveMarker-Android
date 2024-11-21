package com.capstone.lovemarker.feature.upload.photo

import android.net.Uri
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class PhotoState(
    val imageUris: PersistentList<Uri> = persistentListOf()
)
