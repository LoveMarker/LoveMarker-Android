package com.capstone.lovemarker.oauth.di

import com.capstone.lovemarker.oauth.repository.GoogleAuthRepository
import com.capstone.lovemarker.oauth.repository.GoogleAuthRepositoryImpl
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
    abstract fun bindGoogleAuthRepository(
        googleAuthRepositoryImpl: GoogleAuthRepositoryImpl,
    ): GoogleAuthRepository
}
