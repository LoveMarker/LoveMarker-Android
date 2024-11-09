package com.capstone.lovemarker.core.network.authenticator

import android.content.Context
import com.capstone.lovemarker.core.datastore.source.UserPreferencesDataSource
import com.capstone.lovemarker.core.network.service.ReissueTokenService
import com.jakewharton.processphoenix.ProcessPhoenix
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject

class LoveMarkerAuthenticator @Inject constructor(
    private val userPreferencesDataSource: UserPreferencesDataSource,
    private val reissueTokenService: ReissueTokenService,
    @ApplicationContext private val context: Context,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code == CODE_TOKEN_EXPIRED) {
            val newAccessToken = runCatching {
                runBlocking {
                    reissueTokenService.getNewAccessToken(
                        refreshToken = userPreferencesDataSource.userData.first().refreshToken
                    )
                }.data.accessToken
            }.onSuccess { token ->
                runBlocking {
                    userPreferencesDataSource.updateAccessToken(token)
                }
            }.onFailure { throwable ->
                Timber.e(throwable.message)
                runBlocking {
                    userPreferencesDataSource.clear()
                }
                ProcessPhoenix.triggerRebirth(context)
            }.getOrThrow()

            return response.request.newBuilder()
                .header("Authorization", "Bearer $newAccessToken")
                .build()
        }

        return null
    }

    companion object {
        const val CODE_TOKEN_EXPIRED = 401
    }
}