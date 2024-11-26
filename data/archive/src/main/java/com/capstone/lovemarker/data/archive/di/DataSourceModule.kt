package com.capstone.lovemarker.data.archive.di

import com.capstone.lovemarker.data.archive.source.ArchiveDataSource
import com.capstone.lovemarker.data.archive.source.ArchiveDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindArchiveDataSource(archiveDataSourceImpl: ArchiveDataSourceImpl): ArchiveDataSource
}
