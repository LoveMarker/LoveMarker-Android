package com.capstone.lovemarker.auth.di

import com.capstone.lovemarker.auth.repository.AuthRepository
import com.capstone.lovemarker.auth.repository.AuthRepositoryImpl
import com.capstone.lovemarker.auth.source.AuthDataSource
import com.capstone.lovemarker.auth.source.AuthDataSourceImpl
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
