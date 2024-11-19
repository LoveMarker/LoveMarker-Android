package com.capstone.lovemarker.data.mypage.di

import com.capstone.lovemarker.data.mypage.repository.MyPageRepositoryImpl
import com.capstone.lovemarker.domain.mypage.repository.MyPageRepository
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
    abstract fun bindMyPageRepository(myPageRepositoryImpl: MyPageRepositoryImpl): MyPageRepository
}
