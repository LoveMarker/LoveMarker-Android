package com.capstone.lovemarker.oauth.model

import com.capstone.lovemarker.oauth.entity.GoogleOAuthToken

typealias GoogleToken = com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

fun GoogleToken.toDomainEntity() = GoogleOAuthToken(
    accessToken = this.idToken
)
