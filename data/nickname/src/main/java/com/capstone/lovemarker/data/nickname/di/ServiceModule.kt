package com.capstone.lovemarker.data.nickname.di

import com.capstone.lovemarker.core.network.qualifier.AuthRequired
import com.capstone.lovemarker.data.nickname.service.NicknameService
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
    fun provideNicknameService(@AuthRequired retrofit: Retrofit): NicknameService = retrofit.create()
}
