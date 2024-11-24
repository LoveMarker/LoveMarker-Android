import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.feature)
}

android {
    setNamespace("feature.search")
}

dependencies {
    implementation(libs.bundles.google.map)
    implementation(libs.coil.compose)

    implementation(projects.domain.search)
}