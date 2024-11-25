package com.capstone.lovemarker.domain.upload.entity

data class UploadRequestEntity(
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val date: String,
    val title: String,
    val content: String,
)
