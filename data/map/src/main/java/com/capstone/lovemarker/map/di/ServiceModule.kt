package com.capstone.lovemarker.map.di

import com.capstone.lovemarker.core.network.qualifier.AuthRequired
import com.capstone.lovemarker.map.service.MapService
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
    fun provideMapService(@AuthRequired retrofit: Retrofit): MapService = retrofit.create()
}
