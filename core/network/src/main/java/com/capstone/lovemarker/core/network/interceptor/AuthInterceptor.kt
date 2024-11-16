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
        val userData = runBlocking {
            userPreferencesDataSource.userData.first()
        }

        val originalRequest = chain.request()
        val headerRequest = originalRequest.newBuilder()
            .addHeader("accessToken", userData.accessToken)
            .addHeader("refreshToken", userData.refreshToken)
            .build()

        Timber.d("Headers: ${headerRequest.headers}")

        return chain.proceed(headerRequest)
    }
}
