import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.feature)
}

android {
    setNamespace("feature.upload")
}

dependencies {
    implementation(libs.coil.compose)

//    implementation(projects.domain.upload)
}
