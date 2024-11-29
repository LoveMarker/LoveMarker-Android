package com.capstone.lovemarker.data.detail.di

import com.capstone.lovemarker.data.detail.source.DetailDataSource
import com.capstone.lovemarker.data.detail.source.DetailDataSourceImpl
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
    abstract fun bindDetailDataSource(detailDataSourceImpl: DetailDataSourceImpl): DetailDataSource
}
