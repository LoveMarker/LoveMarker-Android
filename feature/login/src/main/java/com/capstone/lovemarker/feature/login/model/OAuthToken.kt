package com.capstone.lovemarker.feature.login.model

import com.capstone.lovemarker.auth.entity.GoogleOAuthToken

typealias GoogleToken = com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

fun GoogleToken.toDomainEntity() = GoogleOAuthToken(
    accessToken = this.idToken
)
