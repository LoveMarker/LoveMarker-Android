package com.capstone.lovemarker.data.auth.di

import com.capstone.lovemarker.domain.auth.repository.AuthRepository
import com.capstone.lovemarker.data.auth.repository.AuthRepositoryImpl
import com.capstone.lovemarker.data.auth.source.AuthDataSource
import com.capstone.lovemarker.data.auth.source.AuthDataSourceImpl
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
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository
}
