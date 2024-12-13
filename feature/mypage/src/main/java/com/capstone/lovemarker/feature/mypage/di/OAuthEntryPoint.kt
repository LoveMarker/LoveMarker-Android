package com.capstone.lovemarker.feature.mypage.di

import com.capstone.lovemarker.domain.oauth.service.OAuthService
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface OAuthEntryPoint {
    fun googleAuthService(): OAuthService
}
