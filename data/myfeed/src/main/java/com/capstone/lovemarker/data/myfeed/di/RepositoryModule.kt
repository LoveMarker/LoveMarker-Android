package com.capstone.lovemarker.data.myfeed.di

import com.capstone.lovemarker.data.myfeed.repository.MyFeedRepositoryImpl
import com.capstone.lovemarker.domain.myfeed.repository.MyFeedRepository
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
    abstract fun bindMyFeedRepository(myFeedRepositoryImpl: MyFeedRepositoryImpl): MyFeedRepository
}
