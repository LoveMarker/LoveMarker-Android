package com.capstone.lovemarker.feature.mypage

sealed interface MyPageSideEffect {
    data object NavigateToMatching: MyPageSideEffect

    data object NavigateToNickname: MyPageSideEffect

    data class ShowErrorSnackbar(
        val throwable: Throwable
    ): MyPageSideEffect
}
