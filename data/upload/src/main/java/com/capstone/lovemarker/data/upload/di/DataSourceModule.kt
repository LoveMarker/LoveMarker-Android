package com.capstone.lovemarker.data.upload.di

import com.capstone.lovemarker.data.upload.source.UploadDataSource
import com.capstone.lovemarker.data.upload.source.UploadDataSourceImpl
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
    abstract fun bindUploadDataSource(uploadDataSourceImpl: UploadDataSourceImpl): UploadDataSource
}
