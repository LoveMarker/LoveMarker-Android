package com.capstone.lovemarker.auth.repository

interface AuthRepository {
    suspend fun login()
    suspend fun logout()
}