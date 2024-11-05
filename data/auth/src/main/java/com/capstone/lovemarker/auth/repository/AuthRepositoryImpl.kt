package com.capstone.lovemarker.auth.repository

import com.capstone.lovemarker.auth.source.AuthDataSource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun login() {
        TODO("Not yet implemented")
    }
}