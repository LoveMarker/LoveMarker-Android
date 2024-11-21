package com.capstone.lovemarker.feature.upload.photo

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class PhotoState(
    val images: PersistentList<String> = persistentListOf()
)
