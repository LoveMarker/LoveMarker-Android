package com.capstone.lovemarker.feature.archive

import com.capstone.lovemarker.domain.archive.entity.MemoryEntity
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class ArchiveState(
    val coupleConnected: Boolean = false,
    val showMatchingDialog: Boolean = true,
    val isLoadPage: Boolean = false,
    val memories: PersistentList<MemoryEntity> = persistentListOf()
)
