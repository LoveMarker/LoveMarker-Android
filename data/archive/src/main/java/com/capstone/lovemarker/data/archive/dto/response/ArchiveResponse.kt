package com.capstone.lovemarker.data.archive.dto.response

import com.capstone.lovemarker.domain.archive.entity.ArchiveEntity
import com.capstone.lovemarker.domain.archive.entity.Memory
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArchiveResponse(
    @SerialName("pageInfo")
    val pageInfo: Page,
    @SerialName("memories")
    val memories: List<Memory>
) {
    @Serializable
    data class Page(
        @SerialName("totalElements")
        val totalElements: Int,
        @SerialName("hasNext")
        val hasNext: Boolean
    )

    @Serializable
    data class Memory(
        @SerialName("memoryId")
        val memoryId: Int,
        @SerialName("title")
        val title: String,
        @SerialName("date")
        val date: String,
        @SerialName("address")
        val address: String,
        @SerialName("image")
        val image: String
    )
}

fun ArchiveResponse.toDomain() = ArchiveEntity(
    hasNextPage = pageInfo.hasNext,
    memories = memories.map { memory ->
        Memory(
            memoryId = memory.memoryId,
            title = memory.title,
            date = memory.date,
            address = memory.address,
            imageUrl = memory.image
        )
    }
)
