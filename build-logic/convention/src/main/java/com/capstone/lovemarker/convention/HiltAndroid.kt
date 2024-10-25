package com.capstone.lovemarker.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.api.Plugin

internal fun Project.configureHiltAndroid() {
    with(pluginManager) {
        apply("dagger.hilt.android.plugin")
        apply("com.google.devtools.ksp")
    }

    dependencies {
        "implementation"(libs.findLibrary("hilt-android").get())
        "ksp"(libs.findLibrary("hilt-android-compiler").get())
    }
}
