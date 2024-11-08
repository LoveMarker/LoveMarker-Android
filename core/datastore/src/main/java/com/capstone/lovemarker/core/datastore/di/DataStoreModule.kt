package com.capstone.lovemarker.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.capstone.lovemarker.core.datastore.source.UserPreferencesDataSource
import com.capstone.lovemarker.core.datastore.source.UserPreferencesDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    private const val USER_DATASTORE_NAME = "user_preferences"

    private val Context.userDataStore by preferencesDataStore(name = USER_DATASTORE_NAME)

    @Provides
    @Singleton
    fun provideUserDataStore(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> = context.userDataStore

    @Module
    @InstallIn(SingletonComponent::class)
    interface Binder {
        @Singleton
        @Binds
        fun bindUserPreferencesDataSource(
            userPreferencesDataSource: UserPreferencesDataSourceImpl
        ): UserPreferencesDataSource
    }
}
