import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.feature)
}

android {
    setNamespace("feature.main")
}

dependencies {
    implementation(projects.feature.login)
    implementation(projects.feature.nickname)
    implementation(projects.feature.map)

    implementation(projects.domain.auth)
}
