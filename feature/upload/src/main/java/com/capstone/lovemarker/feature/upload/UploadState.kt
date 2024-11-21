package com.capstone.lovemarker.feature.upload

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class UploadState(
    val images: PersistentList<String> = persistentListOf(),
    val latLng: Pair<Double, Double> = Pair(0.0, 0.0),
    val address: String = "",
    val date: String = "",
    val title: String = "",
    val content: String = "",
)
