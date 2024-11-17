package com.capstone.lovemarker.data.matching.di

import com.capstone.lovemarker.data.matching.source.MatchingDataSource
import com.capstone.lovemarker.data.matching.source.MatchingDataSourceImpl
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
    abstract fun bindMatchingDataSource(matchingDataSourceImpl: MatchingDataSourceImpl): MatchingDataSource
}
