package com.capstone.lovemarker.feature.mypage

sealed interface MyPageSideEffect {
    data object NavigateToMatching: MyPageSideEffect

    data class NavigateToNickname(
        val nickname: String
    ): MyPageSideEffect

    data object NavigateToMyFeed: MyPageSideEffect

    data object RestartApp: MyPageSideEffect

    data class ShowErrorSnackbar(
        val throwable: Throwable
    ): MyPageSideEffect
}
