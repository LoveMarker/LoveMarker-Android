package com.capstone.lovemarker.domain.oauth.service

import com.capstone.lovemarker.domain.oauth.entity.OAuthToken

interface OAuthService {
    suspend fun signIn() : OAuthToken
    suspend fun signOut()
}