package com.capstone.lovemarker.data.mypage.repository

import com.capstone.lovemarker.data.mypage.source.MyPageDataSource
import com.capstone.lovemarker.domain.mypage.repository.MyPageRepository
import javax.inject.Inject

class MyPageRepositoryImpl @Inject constructor(
    private val myPageDataSource: MyPageDataSource,
) : MyPageRepository {
    override suspend fun deleteCouple(): Result<Unit> = runCatching {
        myPageDataSource.deleteCouple()
    }
}