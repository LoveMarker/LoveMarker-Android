package com.capstone.lovemarker.auth.di

import com.capstone.lovemarker.auth.service.AuthService
import com.capstone.lovemarker.core.network.qualifier.AuthRequired
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
    fun provideAuthService(@AuthRequired retrofit: Retrofit): AuthService = retrofit.create()
}
