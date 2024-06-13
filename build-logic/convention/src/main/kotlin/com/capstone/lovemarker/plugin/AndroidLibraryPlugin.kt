package com.capstone.lovemarker.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        plugins.apply("com.android.library")
        configureAndroidCommonPlugin()
    }
}