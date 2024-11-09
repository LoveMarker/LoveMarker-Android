package com.capstone.lovemarker.feature.login.di

import com.capstone.lovemarker.oauth.service.OAuthService
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface OAuthEntryPoint {
    fun googleAuthService(): OAuthService
}
