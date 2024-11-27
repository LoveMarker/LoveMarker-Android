package com.capstone.lovemarker.domain.mypage.repository

import com.capstone.lovemarker.domain.mypage.entity.CoupleEntity

interface MyPageRepository {
    suspend fun getCoupleInfo(): Result<CoupleEntity>
    suspend fun deleteCouple(): Result<Unit>
}
