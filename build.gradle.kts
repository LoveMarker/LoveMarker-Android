// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
//        classpath(libs.kotlin.gradleplugin)
//        classpath(libs.hilt.plugin)
//        classpath(libs.agp)
    }
}

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.secretsGradle) apply false
}
true // Needed to make the Suppress annotation work for the plugins block