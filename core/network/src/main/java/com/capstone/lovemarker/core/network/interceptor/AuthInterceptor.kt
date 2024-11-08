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
        val accessToken = runBlocking {
            "Bearer ${userPreferencesDataSource.userData.first().accessToken}"
        }

        val originalRequest = chain.request()
        val headerRequest = originalRequest.newBuilder()
            .header("Authorization", accessToken)
            .build()

        return chain.proceed(headerRequest)
    }
}
