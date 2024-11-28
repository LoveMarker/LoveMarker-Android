package com.capstone.lovemarker.data.archive.di

import com.capstone.lovemarker.core.network.qualifier.AuthRequired
import com.capstone.lovemarker.data.archive.service.ArchiveService
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
    fun provideArchiveService(@AuthRequired retrofit: Retrofit): ArchiveService = retrofit.create()
}
