package com.capstone.lovemarker.core.network.interceptor

import com.capstone.lovemarker.core.datastore.source.UserPreferencesDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
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
            .header(AUTH_HEADER_NAME, userData.accessToken)
            .build()

        return chain.proceed(headerRequest)
    }

    companion object {
        private const val AUTH_HEADER_NAME = "accessToken"
    }
}
