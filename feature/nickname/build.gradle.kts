import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.feature)
}

android {
    setNamespace("feature.nickname")
}

dependencies {
    implementation(projects.domain.nickname)
    implementation(libs.retrofit)
}
