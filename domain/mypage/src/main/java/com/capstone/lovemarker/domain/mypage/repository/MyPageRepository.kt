package com.capstone.lovemarker.domain.mypage.repository

interface MyPageRepository {
    suspend fun deleteCouple(): Result<Unit>
}
