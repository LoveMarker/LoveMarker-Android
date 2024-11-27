package com.capstone.lovemarker.core.network.authenticator

import android.content.Context
import com.capstone.lovemarker.core.datastore.source.UserDataStore
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
    private val userDataStore: UserDataStore,
    private val reissueTokenService: ReissueTokenService,
    @ApplicationContext private val context: Context,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code == CODE_TOKEN_EXPIRED) {
            val newAccessToken = runCatching {
                runBlocking {
                    reissueTokenService.getNewAccessToken(
                        refreshToken = userDataStore.userData.first().refreshToken
                    )
                }.data.accessToken
            }.onSuccess { token ->
                runBlocking {
                    userDataStore.updateAccessToken(token)
                }
            }.onFailure { throwable ->
                Timber.e("FAIL REISSUE TOKEN: ${throwable.message}")
                runBlocking {
                    userDataStore.clear()
                }
                ProcessPhoenix.triggerRebirth(context)
            }.getOrThrow()

            return response.request.newBuilder()
                .header("accessToken", newAccessToken)
                .build()
        }

        return null
    }

    companion object {
        const val CODE_TOKEN_EXPIRED = 401
    }
}