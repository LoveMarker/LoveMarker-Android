import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.feature)
}

android {
    setNamespace("feature.upload")
}

dependencies {
    implementation(libs.bundles.google.map)
    implementation(libs.coil.compose)
    implementation(libs.kotlinx.serialization.json)

    implementation(projects.domain.upload)
}
