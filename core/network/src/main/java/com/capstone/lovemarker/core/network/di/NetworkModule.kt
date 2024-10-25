package com.capstone.lovemarker.core.network.di

import com.capstone.lovemarker.core.network.BuildConfig
import com.capstone.lovemarker.core.network.authenticator.DemoAuthenticator
import com.capstone.lovemarker.core.network.interceptor.AuthInterceptor
import com.capstone.lovemarker.core.network.qualifier.Auth
import com.capstone.lovemarker.core.network.qualifier.Logging
import com.capstone.lovemarker.core.network.qualifier.Secured
import com.capstone.lovemarker.core.network.qualifier.Unsecured
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val CONTENT_TYPE = "application/json"

    @Singleton
    @Provides
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        prettyPrint = true
    }

    @Singleton
    @Provides
    fun provideJsonConverterFactory(json: Json): Converter.Factory {
        return json.asConverterFactory(CONTENT_TYPE.toMediaType())
    }

    @Logging
    @Singleton
    @Provides
    fun provideLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        )

    @Auth
    @Singleton
    @Provides
    fun provideAuthInterceptor(interceptor: AuthInterceptor): Interceptor = interceptor

    @Secured
    @Singleton
    @Provides
    fun provideOkHttpClient(
        @Logging loggingInterceptor: Interceptor,
        @Auth authInterceptor: Interceptor,
        authenticator: Authenticator
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .authenticator(authenticator)
        .build()

    @Unsecured
    @Singleton
    @Provides
    fun provideOkHttpClientUnsecured(
        @Logging loggingInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    @Secured
    @Singleton
    @Provides
    fun provideRetrofit(
        @Secured client: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .baseUrl("")
        .client(client)
        .addConverterFactory(converterFactory)
        .build()

    @Unsecured
    @Singleton
    @Provides
    fun provideRetrofitUnsecured(
        @Unsecured client: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .baseUrl("")
        .client(client)
        .addConverterFactory(converterFactory)
        .build()
}

@Module
@InstallIn(SingletonComponent::class)
interface AuthenticatorBinder {
    @Binds
    @Singleton
    fun provideAuthenticator(authenticator: DemoAuthenticator): Authenticator
}
