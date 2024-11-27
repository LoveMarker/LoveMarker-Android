package com.capstone.lovemarker.feature.archive

import com.capstone.lovemarker.domain.archive.entity.MemoryEntity
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class ArchiveState(
    val memories: PersistentList<MemoryEntity> = persistentListOf()
)
