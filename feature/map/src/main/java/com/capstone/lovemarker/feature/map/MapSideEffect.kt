package com.capstone.lovemarker.feature.map

sealed interface MapSideEffect {
    data object NavigateToMatching: MapSideEffect
}
