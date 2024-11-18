package com.capstone.lovemarker.data.mypage.di

import com.capstone.lovemarker.data.mypage.service.MyPageService
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
    fun provideMyPageService(@AuthRequired retrofit: Retrofit): MyPageService = retrofit.create()
}
