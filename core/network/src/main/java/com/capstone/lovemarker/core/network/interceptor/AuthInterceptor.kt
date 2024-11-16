package com.capstone.lovemarker.core.network.interceptor

import com.capstone.lovemarker.core.datastore.source.UserPreferencesDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val userPreferencesDataSource: UserPreferencesDataSource,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking {
            "Bearer ${userPreferencesDataSource.userData.first().accessToken}"
        }
        val refreshToken = runBlocking {
            userPreferencesDataSource.userData.first().refreshToken
        }

        Timber.d("Access Token: $accessToken")
        Timber.d("Refresh Token: $refreshToken")

        val originalRequest = chain.request()
        val headerRequest = originalRequest.newBuilder()
            .addHeader("Authorization", accessToken)
            .addHeader("refreshToken", refreshToken)
            .build()

        return chain.proceed(headerRequest)
    }
}
