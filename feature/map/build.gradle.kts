import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.feature)
}

android {
    setNamespace("feature.map")
}

dependencies {
    implementation(libs.bundles.google.map)
}
