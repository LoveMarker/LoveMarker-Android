package com.capstone.lovemarker.data.oauth.di

import com.capstone.lovemarker.data.oauth.service.GoogleAuthService
import com.capstone.lovemarker.oauth.service.OAuthService
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
    abstract fun bindGoogleAuthService(
        googleAuthService: GoogleAuthService,
    ): OAuthService
}
