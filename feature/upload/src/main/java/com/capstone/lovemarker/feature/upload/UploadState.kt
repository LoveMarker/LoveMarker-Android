package com.capstone.lovemarker.feature.upload

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class UploadState(
    val images: PersistentList<String> = persistentListOf()
)
