package com.capstone.lovemarker.domain.mypage.repository

import com.capstone.lovemarker.domain.mypage.entity.MyPageEntity

interface MyPageRepository {
    suspend fun getCoupleInfo(): Result<MyPageEntity>
    suspend fun deleteCouple(): Result<Unit>
}
