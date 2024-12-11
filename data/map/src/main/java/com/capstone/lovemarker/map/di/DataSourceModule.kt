package com.capstone.lovemarker.map.di

import com.capstone.lovemarker.map.source.MapDataSource
import com.capstone.lovemarker.map.source.MapDataSourceImpl
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
    abstract fun bindMapDataSource(mapDataSourceImpl: MapDataSourceImpl): MapDataSource
}
