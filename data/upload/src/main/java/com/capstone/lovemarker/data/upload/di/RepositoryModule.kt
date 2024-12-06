package com.capstone.lovemarker.data.upload.di

import com.capstone.lovemarker.data.upload.repository.UploadRepositoryImpl
import com.capstone.lovemarker.domain.upload.repository.UploadRepository
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
    abstract fun bindUploadRepository(uploadRepositoryImpl: UploadRepositoryImpl): UploadRepository
}
