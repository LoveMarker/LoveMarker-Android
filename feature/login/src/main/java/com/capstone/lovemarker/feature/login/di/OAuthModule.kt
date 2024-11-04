package com.capstone.lovemarker.feature.login.di

import com.capstone.lovemarker.feature.login.service.GoogleAuthService
import com.capstone.lovemarker.feature.login.service.OAuthService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object OAuthModule {
    @Module
    @InstallIn(ActivityComponent::class)
    interface Binder {
        @Binds
        fun bindGoogleAuthService(service: GoogleAuthService): OAuthService
    }
}
