package com.capstone.lovemarker.core.navigation

import com.capstone.lovemarker.core.model.SearchPlace
import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Splash: Route

    @Serializable
    data object Login : Route

    @Serializable
    data class Nickname(
        val prevRouteName: String, // login or mypage
        val currentNickname: String? = null
    ) : Route

    @Serializable
    data class Detail(
        val memoryId: Int,
    ) : Route

    @Serializable
    data object MyFeed: Route
}

sealed interface MatchingRoute: Route {
    @Serializable
    data class Home(
        val prevRouteName: String // nickname or main tab
    ) : MatchingRoute

    @Serializable
    data class Sender(
        val prevRouteName: String
    ): MatchingRoute

    @Serializable
    data class Receiver(
        val prevRouteName: String
    ) : MatchingRoute
}

sealed interface UploadRoute: Route {
    @Serializable
    data object Photo: UploadRoute

    @Serializable
    data class Content(
        val searchPlace: SearchPlace? = null
    ): UploadRoute

    @Serializable
    data object PlaceSearch : UploadRoute
}

sealed interface MainTabRoute : Route {
    @Serializable
    data object Map : MainTabRoute

    @Serializable
    data object Archive : MainTabRoute

    @Serializable
    data class MyPage(
        val modifiedNickname: String? = null
    ) : MainTabRoute
}