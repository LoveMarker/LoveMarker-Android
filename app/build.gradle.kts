import java.util.Properties

plugins {
    alias(libs.plugins.lovemarker.android.application)
}

val properties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}

android {
    namespace = "com.capstone.lovemarker"

    defaultConfig {
        applicationId = "com.capstone.lovemarker"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        manifestPlaceholders["MAPS_API_KEY"] = properties.getProperty("maps.api.key")
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // feature
    implementation(projects.feature.main)

    // data
    implementation(projects.data.auth)
    implementation(projects.data.oauth)
    implementation(projects.data.nickname)
    implementation(projects.data.matching)
    implementation(projects.data.mypage)

    // android
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.navigation)
    implementation(libs.fragment.ktx)
    implementation(libs.material)

    // test
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.android.test)

    // other
    implementation(libs.timber)
}