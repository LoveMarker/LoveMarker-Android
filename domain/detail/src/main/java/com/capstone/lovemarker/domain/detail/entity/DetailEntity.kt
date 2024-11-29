package com.capstone.lovemarker.domain.detail.entity

data class DetailEntity(
    val title: String,
    val writer: String,
    val address: String,
    val date: String,
    val content: String,
    val images: List<String>
)
