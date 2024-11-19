package com.capstone.lovemarker.data.matching.di

import com.capstone.lovemarker.data.matching.repository.MatchingRepositoryImpl
import com.capstone.lovemarker.domain.matching.repository.MatchingRepository
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
    abstract fun bindMatchingRepository(matchingRepositoryImpl: MatchingRepositoryImpl): MatchingRepository
}
