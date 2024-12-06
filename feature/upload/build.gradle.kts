import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.feature)
}

android {
    setNamespace("feature.upload")
}

dependencies {
//    implementation(projects.domain.upload)
    implementation(libs.bundles.google.maps)
    implementation(libs.kotlinx.serialization.json)
}
