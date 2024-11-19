import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.feature)
}

android {
    setNamespace("feature.main")
}

dependencies {
    // onboarding
    implementation(projects.feature.login)
    implementation(projects.feature.nickname)
    implementation(projects.feature.matching)

    // main tab
    implementation(projects.feature.map)
    implementation(projects.feature.archive)
    implementation(projects.feature.mypage)

    implementation(projects.domain.auth)
}
