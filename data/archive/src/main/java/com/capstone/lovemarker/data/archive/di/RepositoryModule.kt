package com.capstone.lovemarker.data.archive.di

import com.capstone.lovemarker.data.archive.repository.ArchiveRepositoryImpl
import com.capstone.lovemarker.domain.archive.repository.ArchiveRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindArchiveRepository(archiveRepositoryImpl: ArchiveRepositoryImpl): ArchiveRepository
}
