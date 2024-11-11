package com.capstone.lovemarker.data.nickname.di

import com.capstone.lovemarker.data.nickname.repository.NicknameRepositoryImpl
import com.capstone.lovemarker.domain.nickname.repository.NicknameRepository
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
    abstract fun bindNicknameRepository(nicknameRepositoryImpl: NicknameRepositoryImpl): NicknameRepository
}
