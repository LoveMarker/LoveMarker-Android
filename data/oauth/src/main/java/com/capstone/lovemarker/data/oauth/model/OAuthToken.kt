package com.capstone.lovemarker.data.oauth.model

import com.capstone.lovemarker.domain.oauth.entity.GoogleOAuthToken

typealias GoogleToken = com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

fun GoogleToken.toDomainEntity() = GoogleOAuthToken(
    accessToken = this.idToken
)
