import com.capstone.lovemarker.convention.setNamespace
import java.util.Properties

plugins {
    alias(libs.plugins.lovemarker.android.library)
    alias(libs.plugins.kotlinx.serialization)
}

val properties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}

android {
    setNamespace("core.network")

    defaultConfig {
        val authBaseUrl = properties["auth.base.url"] as? String ?: ""
        buildConfigField("String", "AUTH_BASE_URL", authBaseUrl)
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // core
    implementation(projects.core.datastore)

    // other
    implementation(platform(libs.okhttp.bom))
    implementation(libs.bundles.okhttp)
    implementation(libs.bundles.retrofit)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.timber)
    implementation(libs.process.phoenix)
}
