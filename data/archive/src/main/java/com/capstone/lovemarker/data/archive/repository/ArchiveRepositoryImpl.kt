package com.capstone.lovemarker.data.archive.repository

import com.capstone.lovemarker.data.archive.source.ArchiveDataSource
import javax.inject.Inject

class ArchiveRepositoryImpl @Inject constructor(
    private val archiveDataSource: ArchiveDataSource
) {

}
