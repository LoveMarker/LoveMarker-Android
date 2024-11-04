package com.capstone.lovemarker.oauth.repository

import com.capstone.lovemarker.oauth.entity.OAuthToken

interface GoogleAuthRepository {
    suspend fun signIn() : OAuthToken
    suspend fun signOut()
}