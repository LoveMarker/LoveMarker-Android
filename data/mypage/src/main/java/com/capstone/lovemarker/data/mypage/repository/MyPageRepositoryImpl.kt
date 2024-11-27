package com.capstone.lovemarker.data.mypage.repository

import com.capstone.lovemarker.data.mypage.source.MyPageDataSource
import com.capstone.lovemarker.domain.mypage.entity.CoupleEntity
import com.capstone.lovemarker.domain.mypage.repository.MyPageRepository
import javax.inject.Inject

class MyPageRepositoryImpl @Inject constructor(
    private val myPageDataSource: MyPageDataSource,
) : MyPageRepository {
    override suspend fun getCoupleInfo(): Result<CoupleEntity> = runCatching {
        myPageDataSource.getCoupleInfo().data.toDomain()
    }

    override suspend fun deleteCouple(): Result<Unit> = runCatching {
        myPageDataSource.deleteCouple()
    }
}
