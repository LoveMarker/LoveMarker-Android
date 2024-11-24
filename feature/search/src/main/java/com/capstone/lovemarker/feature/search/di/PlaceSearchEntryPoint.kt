package com.capstone.lovemarker.feature.search.di

import com.capstone.lovemarker.domain.search.service.PlaceSearchService
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface PlaceSearchEntryPoint {
    fun placeSearchService(): PlaceSearchService
}
