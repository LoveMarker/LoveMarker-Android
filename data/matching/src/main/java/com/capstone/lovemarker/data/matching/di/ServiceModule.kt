package com.capstone.lovemarker.data.matching.di

import com.capstone.lovemarker.core.network.qualifier.AuthRequired
import com.capstone.lovemarker.data.matching.service.MatchingService
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
    fun provideMatchingService(@AuthRequired retrofit: Retrofit): MatchingService = retrofit.create()
}
