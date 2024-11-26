package com.capstone.lovemarker.domain.archive.repository

import com.capstone.lovemarker.domain.archive.entity.ArchiveEntity

interface ArchiveRepository {
    suspend fun getMemories(page: Int, size: Int): Result<ArchiveEntity>
}
