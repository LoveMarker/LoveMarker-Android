package com.capstone.lovemarker.domain.myfeed.entity

data class MyFeedEntity(
    val hasNextPage: Boolean,
    val memories: List<MemoryEntity>
)

data class MemoryEntity(
    val memoryId: Int,
    val imageUrl: String,
    val title: String,
    val address: String,
    val date: String,
)
