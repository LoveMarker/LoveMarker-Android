package com.capstone.lovemarker.data.nickname.di

import com.capstone.lovemarker.data.nickname.source.NicknameDataSource
import com.capstone.lovemarker.data.nickname.source.NicknameDataSourceImpl
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
    abstract fun bindNicknameDataSource(nicknameDataSourceImpl: NicknameDataSourceImpl): NicknameDataSource
}
