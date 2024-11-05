plugins {
    alias(libs.plugins.lovemarker.android.application)
}

android {
    namespace = "com.capstone.lovemarker"

    defaultConfig {
        applicationId = "com.capstone.lovemarker"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    implementation(projects.data.oauth)

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