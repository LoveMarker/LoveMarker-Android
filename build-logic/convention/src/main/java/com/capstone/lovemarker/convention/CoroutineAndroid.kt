package com.capstone.lovemarker.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureCoroutineAndroid() {
    dependencies {
        "implementation"(libs.findLibrary("coroutine").get())
    }
}
