package com.capstone.lovemarker.plugin

import com.android.build.gradle.BaseExtension
import java.util.Properties
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureAndroidCommonPlugin() {
    val properties = Properties().apply {
        load(rootProject.file("local.properties").inputStream())
    }

//    apply<AndroidKotlinPlugin>()
//    apply<KotlinSerializationPlugin>()
    with(plugins) {
        apply("kotlin-parcelize")
    }
//    apply<AndroidHiltPlugin>()

    extensions.getByType<BaseExtension>().apply {
        defaultConfig {
            val baseUrl = properties["baseUrl"] as? String ?: ""
            val googleMapApiKey = properties["googleMapApiKey"] as? String ?: ""

            buildConfigField("String", "BASE_URL", "\"${baseUrl}\"")
            buildConfigField("String", "GOOGLE_MAP_API_KEY", "\"${googleMapApiKey}\"")
        }

        buildFeatures.apply {
            viewBinding = true
            buildConfig = true
        }
    }

    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    dependencies {
        "implementation"(libs.findLibrary("core.ktx").get())
        "implementation"(libs.findLibrary("appcompat").get())
        "implementation"(libs.findBundle("lifecycle").get())
        "implementation"(libs.findLibrary("material").get())
        "implementation"(libs.findLibrary("fragment.ktx").get())
        "implementation"(libs.findLibrary("timber").get())
    }
}