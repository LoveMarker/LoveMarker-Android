package com.capstone.lovemarker.feature.login.di

import com.capstone.lovemarker.feature.login.service.GoogleAuthService
import com.capstone.lovemarker.feature.login.service.OAuthService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class OAuthModule {
    @ActivityScoped
    @Binds
    abstract fun bindGoogleAuthService(service: GoogleAuthService): OAuthService
}
