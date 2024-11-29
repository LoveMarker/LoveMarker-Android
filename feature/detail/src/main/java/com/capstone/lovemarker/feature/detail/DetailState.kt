package com.capstone.lovemarker.feature.detail

import com.capstone.lovemarker.domain.detail.entity.DetailEntity
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

data class DetailState(
    val detailModel: DetailModel = DetailModel()
)

fun DetailEntity.toModel() = DetailModel(
    title = title,
    writer = writer,
    address = address,
    date = date,
    content = content,
    images = images.toPersistentList()
)

data class DetailModel(
    val title: String = "",
    val writer: String = "",
    val address: String = "",
    val date: String = "",
    val content: String = "",
    val images: PersistentList<String> = persistentListOf()
)
