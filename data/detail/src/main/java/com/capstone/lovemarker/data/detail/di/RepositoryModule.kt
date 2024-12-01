package com.capstone.lovemarker.data.detail.di

import com.capstone.lovemarker.data.detail.repository.DetailRepositoryImpl
import com.capstone.lovemarker.domain.detail.repository.DetailRepository
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
    abstract fun bindDetailRepository(detailRepositoryImpl: DetailRepositoryImpl): DetailRepository
}
