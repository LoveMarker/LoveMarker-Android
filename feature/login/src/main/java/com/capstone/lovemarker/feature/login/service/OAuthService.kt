package com.capstone.lovemarker.feature.login.service

import com.capstone.lovemarker.auth.entity.OAuthToken

interface OAuthService {
    suspend fun signIn() : OAuthToken
    suspend fun signOut()
}
