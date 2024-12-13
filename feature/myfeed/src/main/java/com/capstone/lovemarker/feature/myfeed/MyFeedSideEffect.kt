package com.capstone.lovemarker.feature.myfeed

sealed interface MyFeedSideEffect {
    data class ShowErrorSnackbar(
        val throwable: Throwable
    ) : MyFeedSideEffect
}
