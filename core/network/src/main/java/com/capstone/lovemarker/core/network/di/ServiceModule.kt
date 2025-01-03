package com.capstone.lovemarker.core.network.di

import com.capstone.lovemarker.core.network.qualifier.AuthNotRequired
import com.capstone.lovemarker.core.network.service.ReissueTokenService
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
    fun provideReissueTokenService(@AuthNotRequired retrofit: Retrofit): ReissueTokenService =
        retrofit.create()
}
