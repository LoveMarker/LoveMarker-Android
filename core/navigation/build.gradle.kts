import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.android.library)
    alias(libs.plugins.lovemarker.android.compose)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    setNamespace("core.navigation")
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}
