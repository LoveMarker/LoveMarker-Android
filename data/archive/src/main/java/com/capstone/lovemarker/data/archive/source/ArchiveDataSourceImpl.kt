package com.capstone.lovemarker.data.archive.source

import com.capstone.lovemarker.data.archive.service.ArchiveService
import javax.inject.Inject

class ArchiveDataSourceImpl @Inject constructor(
    private val archiveService: ArchiveService
): ArchiveDataSource {
    override suspend fun getMemories(page: Int, size: Int) =
        archiveService.getMemories(page, size)
}
