import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.android.library)
    alias(libs.plugins.lovemarker.android.compose)
}

android {
    setNamespace("core.common")
}

dependencies {
    implementation(platform(libs.okhttp.bom))
    implementation(libs.bundles.okhttp)
    implementation(libs.timber)
    implementation(libs.exif)
}