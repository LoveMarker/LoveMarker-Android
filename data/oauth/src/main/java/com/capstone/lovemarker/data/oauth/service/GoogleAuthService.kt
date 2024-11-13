package com.capstone.lovemarker.data.oauth.service

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialRequest.Builder
import com.capstone.lovemarker.data.oauth.BuildConfig
import com.capstone.lovemarker.domain.oauth.entity.OAuthToken
import com.capstone.lovemarker.data.oauth.model.toDomainEntity
import com.capstone.lovemarker.domain.oauth.service.OAuthService
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class GoogleAuthService @Inject constructor(
    @ActivityContext private val context: Context,
): OAuthService {
    private val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(BuildConfig.WEB_CLIENT_ID)
        .setAutoSelectEnabled(true)
        .build()

    private val request: GetCredentialRequest = Builder()
        .addCredentialOption(googleIdOption)
        .build()

    private val credentialManager = CredentialManager.create(context)

    override suspend fun signIn(): OAuthToken {
        val response = credentialManager.getCredential(
            request = request,
            context = context,
        )

        return when (val credential = response.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        GoogleIdTokenCredential.createFrom(credential.data).toDomainEntity()
                    } catch (e: GoogleIdTokenParsingException) {
                        throw Exception("Received an invalid google id token response", e)
                    }
                } else {
                    // Catch any unrecognized custom credential type here.
                    throw Exception("Unexpected type of custom credential")
                }
            }

            else -> {
                // Catch any unrecognized credential type here.
                throw Exception("Unexpected type of credential")
            }
        }
    }

    override suspend fun signOut() {
        TODO("Not yet implemented")
    }
}