package com.capstone.lovemarker.data.myfeed.di

import com.capstone.lovemarker.core.network.qualifier.AuthRequired
import com.capstone.lovemarker.data.myfeed.service.MyFeedService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideMyFeedService(@AuthRequired retrofit: Retrofit): MyFeedService = retrofit.create()
}
