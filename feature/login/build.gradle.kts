import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.feature)
}

android {
    setNamespace("feature.login")
}

dependencies {
    implementation(projects.domain.auth)
    implementation(projects.domain.oauth)
}
