package com.capstone.lovemarker.map.di

import com.capstone.lovemarker.map.repository.MapRepository
import com.capstone.lovemarker.map.repository.MapRepositoryImpl
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
    abstract fun bindMapRepository(mapRepositoryImpl: MapRepositoryImpl): MapRepository
}
