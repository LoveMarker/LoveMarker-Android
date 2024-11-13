package com.capstone.lovemarker.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    /**
     * 초기 설치: Login -> Nickname -> Matching -> Main (Map)
     * 로그아웃: Login -> Main (Map)
     * */
    @Serializable
    data object Login : Route

    @Serializable
    data object Nickname : Route

    @Serializable
    data object Detail : Route
}

sealed interface CoupleMatching: Route {
    @Serializable
    data object Home : CoupleMatching

    @Serializable
    data object CodeSender : CoupleMatching

    @Serializable
    data object CodeReceiver : CoupleMatching
}

sealed interface Upload: Route {
    @Serializable
    data object Photo : Upload

    @Serializable
    data object Content : Upload

    @Serializable
    data object PlaceSearch : Upload
}

sealed interface MainTabRoute : Route {
    @Serializable
    data object Map : MainTabRoute

    @Serializable
    data object Archive : MainTabRoute

    @Serializable
    data object MyPage : MainTabRoute
}
