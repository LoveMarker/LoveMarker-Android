package com.capstone.lovemarker.core.navigation

import android.os.Bundle
import androidx.navigation.NavType
import com.capstone.lovemarker.core.model.SearchPlace
import kotlinx.serialization.json.Json

val SearchPlaceNavType = object : NavType<SearchPlace?>(isNullableAllowed = true) {
    override fun get(bundle: Bundle, key: String): SearchPlace? {
        return bundle.getString(key)?.let { Json.decodeFromString(it) }
    }

    override fun parseValue(value: String): SearchPlace? {
        return Json.decodeFromString(value)
    }

    override fun put(bundle: Bundle, key: String, value: SearchPlace?) {
        bundle.putString(key, value?.let { Json.encodeToString(SearchPlace.serializer(), it) })
    }
}
