import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.feature)
}

android {
    setNamespace("feature.nickname")
}

dependencies {
    implementation(projects.domain.nickname)
    implementation(projects.core.datastore)
    implementation(libs.retrofit)
}
