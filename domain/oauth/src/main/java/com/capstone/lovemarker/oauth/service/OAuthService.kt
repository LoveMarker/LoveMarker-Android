package com.capstone.lovemarker.oauth.service

import com.capstone.lovemarker.oauth.entity.OAuthToken

interface OAuthService {
    suspend fun signIn() : OAuthToken
    suspend fun signOut()
}