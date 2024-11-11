package com.capstone.lovemarker.domain.oauth.entity

sealed interface OAuthToken {
    val accessToken: String
}

data class GoogleOAuthToken(
    override val accessToken: String
) : OAuthToken

/** 다른 소셜 로그인이 추가되는 경우 */
//data class KakaoOAuthToken(
//    override val accessToken: String,
//    val refreshToken: String
//) : OAuthToken
