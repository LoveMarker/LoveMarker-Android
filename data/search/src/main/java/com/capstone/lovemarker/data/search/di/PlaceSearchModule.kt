package com.capstone.lovemarker.data.search.di

import com.capstone.lovemarker.data.search.service.PlaceSearchServiceImpl
import com.capstone.lovemarker.domain.search.service.PlaceSearchService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class PlaceSearchModule {
    @Binds
    @ActivityScoped
    abstract fun bindPlaceSearchService(
        placeSearchServiceImpl: PlaceSearchServiceImpl
    ) : PlaceSearchService
}
