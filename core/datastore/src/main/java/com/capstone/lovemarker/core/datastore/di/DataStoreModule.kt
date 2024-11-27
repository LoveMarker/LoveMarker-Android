package com.capstone.lovemarker.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.capstone.lovemarker.core.datastore.di.qualifier.Couple
import com.capstone.lovemarker.core.datastore.di.qualifier.User
import com.capstone.lovemarker.core.datastore.source.couple.CoupleDataStore
import com.capstone.lovemarker.core.datastore.source.couple.CoupleDataStoreImpl
import com.capstone.lovemarker.core.datastore.source.user.UserDataStore
import com.capstone.lovemarker.core.datastore.source.user.UserDataStoreImpl
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

    private const val COUPLE_DATASTORE_NAME = "couple_preferences"
    private val Context.coupleDataStore by preferencesDataStore(name = COUPLE_DATASTORE_NAME)

    @User
    @Provides
    @Singleton
    fun provideUserDataStore(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> = context.userDataStore

    @Couple
    @Provides
    @Singleton
    fun provideCoupleDataStore(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> = context.coupleDataStore
}

@Module
@InstallIn(SingletonComponent::class)
interface Binder {
    @Singleton
    @Binds
    fun bindUserDataStore(
        userDataStoreImpl: UserDataStoreImpl
    ): UserDataStore

    @Singleton
    @Binds
    fun bindCoupleDataStore(
        coupleDataStoreImpl: CoupleDataStoreImpl
    ): CoupleDataStore
}
