package com.capstone.lovemarker.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    private const val USER_PREFERENCES_NAME = "user_preferences"
    private val Context.userDataStore by preferencesDataStore(name = USER_PREFERENCES_NAME)

    @Provides
    @Singleton
    fun provideUserDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.userDataStore
}